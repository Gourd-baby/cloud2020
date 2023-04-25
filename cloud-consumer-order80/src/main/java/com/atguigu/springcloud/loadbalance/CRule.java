package com.atguigu.springcloud.loadbalance;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @version 1.0
 * @Author 作者名
 * @Date 2023/4/24 21:08
 * @注释 手写自定义负载均衡算法
 */
public interface CRule {
    //获取 提供服务 （比如cloud-payment-service这个服务，然后这个服务有多个服务实例8001,8002）
    ServiceInstance instance(List<ServiceInstance> instances);
}
