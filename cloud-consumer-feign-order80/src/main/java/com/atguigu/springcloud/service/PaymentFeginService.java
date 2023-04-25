package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @version 1.0
 * @Author 作者名
 * @Date 2023/4/25 2:16
 * @注释
 */
@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE") //对哪个服务实现负载均衡
public interface PaymentFeginService {
    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id")Long id);

    //超时测试
    @GetMapping(value = "/payment/fegintimeout/lb")
    public String feginTimeOut();
}
