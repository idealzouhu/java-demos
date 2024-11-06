package com.zouhu.springboot.filter.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

/**
 * 自定义日志记录过滤器
 *
 * @author zouhu
 * @data 2024-11-06 20:21
 */
//@WebFilter(filterName = "loggingFilter", urlPatterns = "/*") // 拦截所有请求
public class LoggingFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        // 过滤器初始化时的逻辑
        System.out.println("LoggingFilter initialized");
    }

    /**
     * 过滤器执行
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 记录请求信息
        System.out.println("Request received at " + System.currentTimeMillis() + " from IP: " + request.getRemoteAddr());

        // 继续执行后续的过滤器和请求处理链
        chain.doFilter(request, response);

        // 请求完成后记录响应信息
        System.out.println("Response sent at " + System.currentTimeMillis());
    }

    @Override
    public void destroy() {
        // 过滤器销毁时的逻辑
        System.out.println("LoggingFilter destroyed");
    }
}