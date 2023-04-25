package com.atguigu.springcloud;

import com.atguigu.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @version 1.0
 * @Author 作者名
 * @Date 2023/4/12 19:27
 * @注释
 */
@SpringBootApplication
@EnableEurekaClient
//@RibbonClient(name = "cloud-payment-service",configuration = MySelfRule.class) //自定义选择哪种负载均衡算法
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class,args);
    }
}
