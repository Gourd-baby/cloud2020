package spring.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Author 作者名
 * @Date 2023/5/5 18:32
 * @注释
 */
@RestController
public class FlowLimitMethodController {
    /**
     * 限流分为两大类：
     *      1）url限流方式[eg:/testCustomer]
     *          ①url+默认限流方法调用Blocked by Sentinel (flow limiting)
     *
     *      2）资源名称限流方式[eg:testCustomer]
     *          ①资源名称+默认限流方法，500（针对热点限流），流控和降级仍然默认限流方法是Blocked by Sentinel (flow limiting)
     *          ②资源名称+自定义限流方法+不解耦
     *          ③资源名称+自定义限流方法+业务方法和兜底方法解耦  [推荐用这个]
     *
     *          写兜底方法需要注意，修饰符是public,返回值类型和业务方法的返回值类型一致，兜底方法名与blockHandler写的一致
     *          参数中必须有BlockException,如果是热点限流的话，还需加上热点参数
     */

    //第一种   url+默认限流方式
    @GetMapping("/testCustomer")
    public String testCustomer(){
        return "....testCustomer";
    }
    //第二种   资源名称+自定义限流方式+与业务方法解耦
    @GetMapping("/testCustomer1")
    @SentinelResource(value = "testCustomer1",
            blockHandlerClass = CustomerHandlerException.class,
            blockHandler ="CustomerHandlerException1")
    public CommonResult testCustomer1(){
        return new CommonResult(444,"....testCustomer1");
    }
    //第三种   资源名称+默认限流方式
    @GetMapping("/testCustomer2")
    @SentinelResource(value = "testCustomer2")
    public String testCustomer2(){
        return "....testCustomer2";
    }
    //第四种   资源名称+自定义限流方式+与业务方法不解耦，比如下面两端代码放一块
    @GetMapping("/testCustomer3")
    @SentinelResource(value = "testCustomer3",
            blockHandler ="deal_testCustomer3")
    public String testCustomer3(){
        return "....testCustomer3";
    }
    public String deal_testCustomer3(BlockException e){
        return "第四种限流方式：资源名称+自定义限流方式，但是与业务代码耦合";
    }


}
