package com.atguigu.springcloud.loadbalance;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * @Author 作者名
 * @Date 2023/4/24 21:15
 * @注释
 */
@Component
public class CRoundRobinRule implements CRule{

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final Integer getRequestCount(){
        int current;
        int next;
        do {
            current = atomicInteger.get();
            next = current > 2147483647 ? 0 : current + 1;
        }while (!atomicInteger.compareAndSet(current,next));
        System.out.println("****第几次请求："+ next );
        return next;
    }


    @Override
    public  ServiceInstance instance(List<ServiceInstance> instances) {

        int index = getRequestCount() % instances.size();
        ServiceInstance instance = instances.get(index);
        return instance;
    }
}
