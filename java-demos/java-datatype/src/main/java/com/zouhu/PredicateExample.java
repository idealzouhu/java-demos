package com.zouhu;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author zouhu
 * @data 2024-11-11 16:13
 */
public class PredicateExample {
    public static void main(String[] args) {
        // 一个整数列表
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // 创建一个 Predicate，用于判断偶数
        Predicate<Integer> isEven = number -> number % 2 == 0;

        // 使用 Predicate 过滤偶数
        numbers.stream()
                .filter(isEven)  // 只保留符合 isEven 条件的元素
                .forEach(System.out::println);  // 打印偶数
    }
}
