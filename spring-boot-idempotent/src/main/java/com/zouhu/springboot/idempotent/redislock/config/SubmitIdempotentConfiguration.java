package com.zouhu.springboot.idempotent.redislock.config;

import com.zouhu.springboot.idempotent.redislock.SubmitIdempotentAspect;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
public class SubmitIdempotentConfiguration {

    /**
     *  防止用户重复提交的切面实现类
     *
     * @param redissonClient
     * @return
     */
    @Bean
    public SubmitIdempotentAspect noDuplicateSubmitAspect(RedissonClient redissonClient) {
        return new SubmitIdempotentAspect(redissonClient);
    }
}
