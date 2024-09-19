package com.zouhu.springboot.idempotent.rocketmq.config;


import com.zouhu.springboot.idempotent.rocketmq.ConsumeIdempotentAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 配置切面类
 * <p>
 *     如果切面类没有作为 Bean 被 Spring 管理，则无法生效
 * </p>
 *
 * @author zouhu
 * @data 2024-09-18 21:48
 */
@Configuration
public class ConsumeIdempotentConfiguration {

    /**
     *  防止消息队列中消费者重复消费消息的切面实现类
     *
     * @param stringRedisTemplate
     * @return
     */
    @Bean
    public ConsumeIdempotentAspect consumeIdempotentAspect(StringRedisTemplate stringRedisTemplate) {
        return new ConsumeIdempotentAspect(stringRedisTemplate);
    }
}
