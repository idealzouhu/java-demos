package com.zouhu.springboot.idempotent.redislock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 幂等注解
 * <p>
 *     利用 Redis 的分布式锁实现接口防重复提交
 * </p>
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 触发幂等失败逻辑时，返回的错误提示信息
     */
    String message() default "操作过于频繁，请稍后再试！";
}
