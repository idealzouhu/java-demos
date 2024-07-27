package com.zouhu.aop.config;

import com.zouhu.aop.log.ILog;
import com.zouhu.aop.log.ILogPrintAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志自动装配
 * <p>
 *    LogAutoConfiguration 为自动配置类。 自动配置类会被放置在 META-INF/spring.factories 文件中，并
 *    且会以 EnableAutoConfiguration 注解的形式被 Spring Boot 启用。
 * </p>
 *
 * @author zouhu
 * @data 2024-07-27 18:46
 */
//@Configuration
public class LogAutoConfiguration {
    /**
     * {@link ILog} 日志打印 AOP 切面
     */
    @Bean
    public ILogPrintAspect iLogPrintAspect() {
        return new ILogPrintAspect();
    }

}
