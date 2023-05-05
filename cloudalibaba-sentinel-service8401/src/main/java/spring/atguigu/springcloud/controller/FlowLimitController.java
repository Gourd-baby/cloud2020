package spring.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.atguigu.springcloud.serverice.SentinelService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author 作者名
 * @Date 2023/5/4 15:19
 * @注释
 */
@RestController
@Slf4j
public class FlowLimitController
{
    @Resource
    private SentinelService sentinelService;

    @GetMapping("/testA")
    public String testA() throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(800);演示线程数达到阈值，则限流
        return "------testA"+sentinelService.sentinelChain();
    }

    @GetMapping("/testB")
    public String testB()
    {
        log.info("当前线程名称："+Thread.currentThread().getName());
        return "------testB"+sentinelService.sentinelChain();
    }
    /**
     * sentinel服务降级演示：三种： 满足以条件发生降级-------借助postman
     * 1）RT(qps>=5  &&  实际的RT  >=  设置的RT【即阈值】  发生降级)
     * 2）异常比例（qps>=5  &&  实际的异常比例 >= 设置的异常比例【即阈值】  发生降级）
     * 3） 异常数 （分钟级别，在一分钟内实际的异常数 >= 设置的一分钟允许的异常数  发生降级）
     */

    @GetMapping("/testService")
    public String testService() throws InterruptedException {
        //演示降级第一种情况，可以理解未Hytrix的超时
        //TimeUnit.SECONDS.sleep(1);

        //演示第二种情况，可以理解为异常
        //int i = 10 / 0;

        //演示第三种情况
//        int i = 10 /0;
        return "========testService() ";
    }

    /**
     * 热点限流 hotkey，对参数的限流
     */
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1" ,required = false) String p1,
                             @RequestParam(value = "p2",required = false) String p2){

        return "-------testHotKey";

    }

    //这个方法是对上面的补充，当上面产生热点限流之后，执行这个方法
    public String deal_testHotKey(String p1, String p2, BlockException exception){
        return "热点限流了....";
    }
}
