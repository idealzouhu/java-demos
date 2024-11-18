package com.zouhu;

/**
 * Object 类
 *
 * @author zouhu
 * @data 2024-11-11 15:19
 */
public class ObjectExample {
    public static void main(String[] args) {
        Object obj = new Object();
        System.out.println(obj.toString());  // 输出：java.lang.Object@6f94fa3e

        Class<?> clazz = obj.getClass();
        System.out.println(clazz.getName());  // 输出：java.lang.Object

    }
}
