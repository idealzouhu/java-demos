package com.zouhu.string;

/**
 * @author zouhu
 * @data 2024-08-27 16:53
 */
public class StringExample {
    public static void main(String[] args) {

        // String 类对象不可变
        String s1 = "Hello";
        String s2 = s1.concat(" World");
        System.out.println(s1 == s2);  // false

        // String 类支持字符串池
        String a = "Hello";
        String b = "Hello";
        System.out.println(a == b);  // true
    }
}
