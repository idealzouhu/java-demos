package com.zouhu.strategy.pattern.handler;

import com.zouhu.strategy.pattern.AbstractExecuteStrategy;
import org.springframework.stereotype.Component;

/**
 * 减法策略
 *
 * @author zouhu
 * @data 2024-11-01 15:16
 */
@Component
public class SubtractStrategy  implements AbstractExecuteStrategy<Integer, Integer> {
    @Override
    public String mark() {
        return "SUBTRACT";
    }

    @Override
    public Integer executeResp(Integer requestParam) {
        return requestParam - 5; // 假设每次减5
    }
}
