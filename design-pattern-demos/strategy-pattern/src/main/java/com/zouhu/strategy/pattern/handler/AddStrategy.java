package com.zouhu.strategy.pattern.handler;

import com.zouhu.strategy.pattern.AbstractExecuteStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 加法策略
 *
 * @author zouhu
 * @data 2024-11-01 15:15
 */
@Component
public class AddStrategy implements AbstractExecuteStrategy<Integer, Integer> {
    @Override
    public String mark() {
        return "ADD"; // 确保这里返回 "ADD"
    }

    @Override
    public Integer executeResp(Integer requestParam) {
        return requestParam + 10; // 示例逻辑
    }
}
