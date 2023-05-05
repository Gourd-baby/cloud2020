package spring.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;

/**
 * @version 1.0
 * @Author 作者名
 * @Date 2023/5/5 18:31
 * @注释
 */
public class CustomerHandlerException {
    public static CommonResult CustomerHandlerException1(BlockException exception){
        return new CommonResult(444,"第二种限流方式--CustomerHandlerException1----服务限流");
    }
    public static CommonResult CustomerHandlerException2(BlockException exception){
        return new CommonResult(444,"CustomerHandlerException2----服务限流");
    }
}
