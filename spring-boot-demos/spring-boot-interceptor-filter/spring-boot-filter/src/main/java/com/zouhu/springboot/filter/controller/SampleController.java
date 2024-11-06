package com.zouhu.springboot.filter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟的控制器，用于测试过滤器的效果
 *
 * @author zouhu
 * @data 2024-11-06 20:28
 */
@RestController
public class SampleController {
    /**
     * 测试请求，访问该接口时会触发 `LoggingFilter`
     *
     * @return 返回一段欢迎消息
     */
    @GetMapping("/secure/hello")
    public String hello() {
        return "Hello, this is a secure endpoint!";
    }

    /**
     * 访问不受过滤器拦截的接口
     *
     * @return 返回消息
     */
    @GetMapping("/public/hello")
    public String publicHello() {
        return "This is a public endpoint, no filter applied!";
    }
}
