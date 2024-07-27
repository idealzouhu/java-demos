package com.zouhu.aop.log;

import org.springframework.stereotype.Service;

/**
 * @author zouhu
 * @data 2024-07-27 17:40
 */
@ILog
@Service
public class TestService {
    @ILog
    public void doSomething() {
        System.out.println("TestService.doSomething()");
    }
}
