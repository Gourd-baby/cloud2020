package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @Author 作者名
 * @Date 2023/4/26 0:30
 * @注释
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "服务调用失败，提示来自：paymentInfo_OK";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "服务调用失败，提示来自：paymentInfo_TimeOut";
    }
}
