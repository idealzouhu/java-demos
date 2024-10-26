package com.zouhu.aop.example;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 服务类
 * <p>
 *  该服务类位于 com.zouhu.aop.example 包下，用于测试环绕通知
 *  <code>@Around("execution(* com.zouhu.aop.*.*(..))")</code>
 * </p>
 *
 * @author zouhu
 * @See com.zouhu.aop.example.LoggingAspect
 * @data 2024-07-26 21:32
 */
@Service
public class SampleService {
    public void doSomething() {
        try {
            Thread.sleep(1000); // 模拟耗时操作
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("SampleService.doSomething()");
    }
}
