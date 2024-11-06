package com.zouhu.interceptor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置
 *
 * @author zouhu
 * @data 2024-11-06 19:57
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 注册登录拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/secure/**") // 仅拦截指定路径
                .excludePathPatterns("/login"); // 登录页面不拦截
    }
}
