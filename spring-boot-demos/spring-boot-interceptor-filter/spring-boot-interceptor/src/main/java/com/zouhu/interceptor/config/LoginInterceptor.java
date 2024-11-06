package com.zouhu.interceptor.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 自定义登录拦截器
 *
 * @author zouhu
 * @data 2024-11-06 19:55
 */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 在请求处理前检查用户是否已登录
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 模拟登录检查逻辑
        if (request.getSession().getAttribute("user") == null) {
            System.out.println("User not logged in, redirecting to login page...");
            response.sendRedirect("/login");
            return false; // 中断请求，不再调用控制器方法
        }

        // 如果用户已登录，则允许请求继续进行
        System.out.println("User logged in, request allowed");
        return true;
    }
}
