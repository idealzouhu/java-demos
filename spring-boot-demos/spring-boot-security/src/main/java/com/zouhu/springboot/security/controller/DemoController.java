package com.zouhu.springboot.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouhu
 * @data 2024-11-07 12:02
 */
@RestController
public class DemoController {
    @GetMapping("/user/secure")
    public String userAccess() {
        return "Hello User!";
    }

    @GetMapping("/admin/secure")
    public String adminAccess() {
        return "Hello Admin!";
    }

    @RequestMapping("/")
    public String home() {
        return "Welcome to the Home Page!";
    }
}
