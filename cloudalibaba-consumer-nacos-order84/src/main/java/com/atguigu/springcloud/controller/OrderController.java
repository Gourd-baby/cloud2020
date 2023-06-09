package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.atguigu.springcloud.entities.CommonResult;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Author 作者名
 * @Date 2023/5/6 9:44
 * @注释
 */
@RestController
public class OrderController {

    @Resource
    private RestTemplate restTemplate;
    //service-url:
    //  nacos-user-service:
    @Value("${service-url.nacos-user-service}")
    private String paymentService;

    @GetMapping("/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback",fallback = "handlerFallback")
//    @SentinelResource(value = "fallback",blockHandler = "blockHandler") //blockHandler负责在sentinel里面配置的降级限流
    @SentinelResource(value = "fallback",fallback = "handlerFallback",blockHandler = "blockHandler",
                        exceptionsToIgnore = {IllegalArgumentException.class}) //excptionsToIgnore让fallback兜底方法不生效
    public CommonResult fallback(@PathVariable("id") Long id){

        CommonResult<Payment> result = restTemplate.getForObject(paymentService + "/paymentSQL/" + id, CommonResult.class);

        if (id == 4) {
            throw new IllegalArgumentException ("IllegalArgumentException,非法参数异常....");
        }else if (result.getData() == null) {
            throw new NullPointerException ("NullPointerException,该ID没有对应记录,空指针异常");
        }

        return result;
    }

    public CommonResult handlerFallback(@PathVariable  Long id,Throwable e) {
        Payment payment = new Payment(id,"null");
        return new CommonResult<>(444,"兜底异常handlerFallback,exception内容  "+e.getMessage(),payment);
    }
    public CommonResult blockHandler(@PathVariable  Long id, BlockException blockException) {
        Payment payment = new Payment(id,"null");
        return new CommonResult<>(445,"blockHandler-sentinel限流,无此流水: blockException  "+blockException.getMessage(),payment);
    }


    //==================OpenFeign
    @Resource
    private PaymentService paymentServiceFeign;

    @GetMapping(value = "/consumer/openfeign/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id)
    {
        if(id == 4)
        {
            throw new RuntimeException("没有该id");
        }
        return paymentServiceFeign.paymentSQL(id);
    }


}
