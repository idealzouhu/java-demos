package com.zouhu.spring.cloud.sentinel.controller;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.CommandLineRunner;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 测试 Sentinel 组件如何限流、熔断降级
 *
 * @author zouhu
 * @data 2024-09-09 22:07
 */
@RestController
public class SentinelController {

    /**
     * 测试限流规则的使用
     *  <p>
     *      定义资源  "SentinelController#testFlowRule"。
     *      限流规则  {@link com.zouhu.spring.cloud.sentinel.config.FlowRuleInit}。
     *      方法调用链接： http://localhost:9000/flow/10。
     *  </p>
     *
     *
     * @return
     */
    @GetMapping("/flow/{id}")
    @SentinelResource(value = "SentinelController#testFlowRule", blockHandler = "handleBlock")
    public String testFlowRule(@PathVariable("id") int id) {
        return "Request success!" + id;
    }

    /**
     * 测试熔断降级规则的使用(存在问题)
     * <p>
     *      定义资源  "SentinelController#testFlowRule"。
     *      限流规则  {@link com.zouhu.spring.cloud.sentinel.config.FlowRuleInit}。
     *      方法调用链接： http://localhost:8080/degrade/10。
     * </p>
     *
     * @param id
     * @return
     */
    @GetMapping("/degrade/{id}")
    @SentinelResource(value = "SentinelController#testDegradeRule", blockHandler = "handleBlock")
    public String testDegradeRule(@PathVariable("id") int id) throws FlowException {
        // 模拟可能抛出的异常
        // 注意：熔断降级规则针对的异常只是 BlockException 以及其子类，不针对其他异常
        if (new Random().nextInt(10) < 8) {
            throw new FlowException("Simulated FlowException");
        }
        return "Request success!" + id;
    }

    /**
     * blockHandler 函数
     * <p>
     *     在资源请求被限流/降级/系统保护的时候调用, 相当于替换原方法。
     *     处理 BlockException。
     * </p>
     * @param ex
     * @return
     */
    public String handleBlock(int id, BlockException ex) {
        return "Request blocked!";
    }

    /**
     * 测试 fallback 规则的使用
     * <p>
     *     定义资源  "SentinelController#testFlowRule"。
     *     方法调用链接： http://localhost:8080/degrade/10。
     * </p>
     *
     * @return
     */
    @GetMapping("/testFallback")
    @SentinelResource(value = "SentinelController#testFallback", fallback = "handleFallback")
    public String testFallback() {
        // 可能抛出运行时异常的业务逻辑
        if (new Random().nextInt(10) < 5) {
            throw new RuntimeException("Simulated error");
        }
        return "Request success!";
    }

    /**
     * fallback 函数
     * <p>
     *       当业务异常或降级规则触发时调用此方法
     * </p>
     *
     * @param ex
     * @return
     */
    public String handleFallback(Throwable ex) {
        return "Fallback due to exception: " + ex.getMessage();
    }

    /**
     * 测试组合规则的使用
     * <p>
     *     定义资源  "SentinelController#testCombined"。
     *     若 blockHandler 和 fallback 都进行了配置，则被限流降级而抛出 `BlockException` 时,
     *     只会进入 `blockHandler` 处理逻辑。
     * </p>
     *
     * @return
     */
    @GetMapping("/testCombined")
    @SentinelResource(value = "SentinelController#testCombined", blockHandler = "handleBlock", fallback = "handleFallback")
    public String testCombined() {
        // 模拟抛出异常
        if (new Random().nextInt(10) < 5) {
            throw new RuntimeException("Simulated error");
        }
        return "Request success!";
    }

}
