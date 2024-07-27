package com.zouhu.aop.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试环绕通知
 * <p>
 *  在这个测试类中，我们注入了SampleService实例，并在测试方法testDoSomething中调用了doSomething方法。
 *  由于SampleService位于com.example.service包下，因此LoggingAspect的切面会生效，从而在控制台输出日志信息。
 * </p>
 *
 * @author zouhu
 * @create 2023-09-07 16:06
 */

@SpringBootTest
public class LoggingAspectTest {
    @Autowired
    private SampleService sampleService;

    @Test
    public void testDoSomething() throws Exception {
        sampleService.doSomething();
    }

}