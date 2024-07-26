package com.zouhu.annotation;

import java.lang.reflect.Method;

/**
 * 测试自定义注解
 *
 * @author zouhu
 * @data 2024-07-26 15:30
 */
public class AnnotationExample {
    /**
     * 使用自定义注解
     */
    @MyAnnotation(value = "Test", number = 5, description = "This is a test method")
    public void testMethod() {
        // 方法实现
    }

    /**
     * 通过反射在运行时获取注解信息
     *
     * @param args
     */
    public static void main(String[] args) throws NoSuchMethodException {
        Method method = AnnotationExample.class.getMethod("testMethod");
        MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);

        if (annotation != null) {
            System.out.println("value: " + annotation.value());
            System.out.println("number: " + annotation.number());
            System.out.println("description: " + annotation.description());
        }
    }
}
