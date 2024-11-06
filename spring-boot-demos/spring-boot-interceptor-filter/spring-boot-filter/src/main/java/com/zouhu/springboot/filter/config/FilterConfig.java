package com.zouhu.springboot.filter.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zouhu
 * @data 2024-11-06 19:52
 */
@Configuration
public class FilterConfig {
    /**
     * 注册自定义过滤器
     *
     * @return 过滤器注册 bean
     */
    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoggingFilter());
        registrationBean.addUrlPatterns("/secure/*");  // 只拦截 /secure/* 路径下的请求
        return registrationBean;
    }
}