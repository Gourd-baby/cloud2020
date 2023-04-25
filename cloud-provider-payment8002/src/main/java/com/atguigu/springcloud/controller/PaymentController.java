package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @Author 作者名
 * @Date 2023/4/11 22:56
 * @注释
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;



    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/payment/lb")
    public String getServerPort(){
        return serverPort;
    }

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("******插入结果："+result);
        if (result > 0 ){
            return new CommonResult(200,"插入数据成功: serverPort: "+serverPort,result);
        }else {
            return new CommonResult(444,"插入数据失败",null);
        }
    }
    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id")Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("******查询结果："+payment);
        if (payment != null ){
            return new CommonResult(200,"查询数据成功 : serverPort: "+serverPort,payment);
        }else {
            return new CommonResult(444,"查询数据失败",null);
        }
    }

    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping(value = "/payment/discovery")
    public Object getDiscovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("******服务的名称："+service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance instance : instances) {
            log.info("服务实例id: "+instance.getInstanceId()+" 端口："+instance.getPort());
        }
        return this.discoveryClient;
    }
}
