package spring.atguigu.springcloud.serverice;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Author 作者名
 * @Date 2023/5/4 16:41
 * @注释
 */
@Service
public class SentinelService {

    /**
     * @SentinelResource: 可以理解就是一个资源名
     */
    @SentinelResource("myresource")
    public String sentinelChain() {
        return "调用该资源成功！！！！！";
    }

}

