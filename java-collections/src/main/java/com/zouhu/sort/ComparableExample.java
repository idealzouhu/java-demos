package com.zouhu.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author zouhu
 * @data 2024-08-27 21:36
 */
public class ComparableExample {
    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Alice", 30),
                new Person("Bob", 25),
                new Person("Charlie", 35)
        );

        Collections.sort(people); // 排序基于 Person 类里面 compareTo 方法的逻辑
        System.out.println(people); // [Bob (25), Alice (30), Charlie (35)]
    }
}
