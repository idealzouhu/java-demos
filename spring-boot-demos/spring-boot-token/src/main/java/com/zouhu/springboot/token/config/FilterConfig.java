package com.zouhu.springboot.token.config;

import com.zouhu.springboot.token.user.UserTransmitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * 注册拦截器
 *
 * @author zouhu
 * @data 2024-11-07 21:16
 */
public class FilterConfig {
    /**
     * 注册用户上下文拦截器
     *
     * @return 过滤器注册 bean
     */
    @Bean
    public FilterRegistrationBean<UserTransmitFilter> globalUserTransmitFilter() {
        FilterRegistrationBean<UserTransmitFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserTransmitFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
