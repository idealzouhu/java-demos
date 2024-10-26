package com.zouhu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 *
 * @author zouhu
 * @data 2024-07-26 15:30
 */
@Retention(RetentionPolicy.RUNTIME)  // 定义注解的保留策略
@Target(ElementType.METHOD)          // 定义注解的目标
public @interface MyAnnotation {
    // 定义注解的元素（属性）
    String value();                  // 必需元素
    int number() default 0;          // 有默认值的元素
    String description() default ""; // 有默认值的元素
}
