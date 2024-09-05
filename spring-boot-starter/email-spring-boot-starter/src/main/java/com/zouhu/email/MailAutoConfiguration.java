package com.zouhu.email;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 邮件自动配置类
 * <p>
 *     自动配置类 MailAutoConfiguration 负责自动装配 MailService，
 *     并且通过 spring.factories 使 Spring Boot 可以自动加载该配置。
 * </p>
 *
 * @author zouhu
 * @data 2024-09-05 21:17
 */
@AutoConfiguration
@EnableConfigurationProperties(MailProperties.class)  // 启用并注册配置绑定功能
public class MailAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean   // 当容器中未存在指定类型的Bean时，才会创建当前Bean
    public MailService mailService(MailProperties properties) {
        return new MailService(properties);
    }
}
