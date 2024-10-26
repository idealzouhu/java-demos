package com.zouhu.spring.cloud.sentinel.config;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置熔断降级规则
 * <p>
 *     参考资料查看 <a href="https://sentinelguard.io/zh-cn/docs/basic-api-resource-rule.html">熔断降级规则</a> 和
 *  *     <a href="https://sentinelguard.io/zh-cn/docs/circuit-breaking.html">熔断降级</a>
 * </p>
 *
 * @author zouhu
 * @data 2024-09-10 20:26
 */
public class DegradeRuleInit  implements CommandLineRunner {

    /**
     * 根据异常比例熔断
     * <p>
     *     当单位统计时长（statIntervalMs）内请求数目大于设置的最小请求数目，
     *     并且异常的比例大于阈值，则接下来的熔断时长内请求会自动被熔断
     * </p>
     *
     * @param args
     */
    @Override
    public void run(String... args) {
        List<DegradeRule> rules = new ArrayList<>();

        DegradeRule rule1 = new DegradeRule("SentinelController#testDegradeRule")
                .setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType())
                .setCount(0.5) // 阈值为 50% 错误率
                .setMinRequestAmount(20)
                .setStatIntervalMs(1000) // 统计时长 1 秒
                .setTimeWindow(10); // 熔断持续时间 10 秒
        rules.add(rule1);

        DegradeRule rule2 = new DegradeRule("SentinelController#testCombined")
                .setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType())
                .setCount(0.7) // 阈值为 70% 错误率
                .setMinRequestAmount(100)
                .setStatIntervalMs(30000) // 统计时长 30 秒
                .setTimeWindow(10); // 熔断持续时间 10 秒
        rules.add(rule2);

        DegradeRuleManager.loadRules(rules);

    }
}
