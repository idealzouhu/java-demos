package com.zouhu.strategy.pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StrategyPatternApplicationTests {

    @Autowired
    private AbstractStrategyChoose strategyChoose;

    @Test
    void testAddStrategy() {
        // 测试加法策略
        Integer addResult = strategyChoose.chooseAndExecuteResp("ADD", 20);
        System.out.println("Add Strategy Result: " + addResult); // 期望结果：30
        assertEquals(Integer.valueOf(30), addResult);
    }

    @Test
    void contextLoads() {

    }

}
