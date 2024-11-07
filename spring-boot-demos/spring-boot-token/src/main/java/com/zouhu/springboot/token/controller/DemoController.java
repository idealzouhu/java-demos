package com.zouhu.springboot.token.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouhu
 * @data 2024-11-07 20:51
 */
@RestController
public class DemoController {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

}
