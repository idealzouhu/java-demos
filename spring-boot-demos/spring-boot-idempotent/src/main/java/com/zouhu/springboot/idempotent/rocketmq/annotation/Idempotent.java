package com.zouhu.springboot.idempotent.rocketmq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 幂等注解
 * <p>
 *     1. 利用 Redis + 去重表, 防止消息队列中消费者重复消费消息, 实现消息幂等性
 *     2. 使用 Spring 提供的 SpEL 表达式生成幂等 key
 *     3. uniqueKeyPrefix + key 生成全局唯一的幂等标识
 * </p>
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 幂等 key 前缀
     */
    String uniqueKeyPrefix() default "";

    /**
     * 幂等 key
     */
    String key();

    /**
     * 幂等 key 过期时间，单位秒, 默认一小时
     */
    long keyTimeout() default 3600L;

    /**
     * 触发幂等失败逻辑时，返回的错误提示信息
     */
    String message() default "操作过于频繁，请稍后再试！";
}
