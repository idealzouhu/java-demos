package com.zouhu.spring.cloud.sentinel.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置流量控制规则
 * <p>
 *     参考资料查看 <a href="https://sentinelguard.io/zh-cn/docs/basic-api-resource-rule.html">流量控制规则</a> 和
 *     <a href="https://sentinelguard.io/zh-cn/docs/flow-control.html">流量控制</a>
 * </p>
 *
 * @author zouhu
 * @data 2024-09-09 22:17
 */
@Component
public class FlowRuleInit implements CommandLineRunner {
    @Override
    public void run(String... args) {
        List<FlowRule> rules = new ArrayList<>();

        FlowRule rule1 = new FlowRule();
        rule1.setResource("SentinelController#testFlowRule");
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setCount(10); // QPS 限制为 2
        rules.add(rule1);

        FlowRule rule2 = new FlowRule();
        rule2.setResource("SentinelController#testCombined");
        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule2.setCount(2); // QPS 限制为 2
        rules.add(rule2);

        FlowRuleManager.loadRules(rules);
    }
}
