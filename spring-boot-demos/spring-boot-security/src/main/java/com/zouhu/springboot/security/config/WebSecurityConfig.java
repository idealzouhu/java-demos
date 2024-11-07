package com.zouhu.springboot.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Spring Security 配置类
 * <p>
 *     基于角色的权限控制使用案例
 * </p>
 *
 * @author zouhu
 * @data 2024-11-07 11:46
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
    /**
     * 配置自定义用户，并给每个用户分配角色
     */
    @Bean
    protected UserDetailsManager userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(User.withUsername("user")
                .password("{noop}123456")    // 使用 {noop} 前缀表示明文密码，仅用于开发和测试环境，生产环境推荐使用其他加密方式（如 BCrypt）。
                // .roles("USER")               // 分配 USER 角色，角色会自动添加前缀 ROLE_
                .authorities("USER")         // 使用 authorities 而不是 roles，角色不会添加前缀
                .build());

        manager.createUser(User.withUsername("admin")
                .password("{noop}admin123456")
                // .roles("ADMIN")    // 分配 ADMIN 角色，角色会自动添加前缀 ROLE_
                .authorities("ADMIN")
                .build());

        return manager;
    }

    /**
     * 配置角色的访问规则
     */
    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> {
                        // 管理员角色可访问的资源
                        authorize.requestMatchers("/admin/**").hasAuthority("ADMIN");

                        // 用户角色可访问的资源（注释掉的部分，如果未来需要启用可以取消注释）
                        authorize.requestMatchers("/user/**").hasAuthority("USER");

                        // 允许所有角色访问的资源
                        authorize.requestMatchers("/").permitAll();

                        // 其他所有请求都需要身份验证
                        authorize.anyRequest().authenticated();
                })
                .formLogin(withDefaults())  // 启用表单登录，默认登录页面为 /login
                .httpBasic(withDefaults()); // 启用 HTTP Basic Authentication

        return http.build();
    }
}
