package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFeginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Author 作者名
 * @Date 2023/4/25 2:17
 * @注释
 */
@RestController
@Slf4j
public class FeginController {
    @Resource
    private PaymentFeginService paymentFeginService;

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id")Long id){
        return paymentFeginService.getPaymentById(id);
    }

    //超时测试
    @GetMapping(value = "/consumer/payment/fegintimeout/lb")
    public String feginTimeOut(){
        return paymentFeginService.feginTimeOut();
    }
}
