package com.zouhu.strategy.pattern.handler;

import com.zouhu.strategy.pattern.AbstractExecuteStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 加法策略
 *
 * @author zouhu
 * @data 2024-10-31 16:47
 */
@Component
@RequiredArgsConstructor
public class ExampleHandler  implements AbstractExecuteStrategy<String, Void> {

    @Override
    public String mark() {
        return "example";
    }


    @Override
    public void execute(String requestParam) {
        System.out.println("执行example策略");
    }
}
