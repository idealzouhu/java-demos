package com.zouhu.interceptor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author zouhu
 * @data 2024-11-06 20:10
 */
@RestController
public class LoginController {

    /**
     * 登录页面
     *
     * @return 返回登录页面
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // 假设返回一个名为 "login" 的视图
    }

    /**
     * 受保护的资源，仅允许登录用户访问
     *
     * @return 访问受保护资源的消息
     */
    @GetMapping("/secure/home")
    public String secureHome() {
        return "Welcome to the secure section!";
    }
}
