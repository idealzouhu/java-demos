package com.zouhu.set;

import java.util.HashSet;

/**
 * @author zouhu
 * @data 2024-08-22 17:18
 */
public class HashSetExample {
    public static void main(String[] args) {

        HashSet<String> set = new HashSet<>();
        set.add("apple");
        set.add("banana");

        System.out.println(set.add("apple"));
    }
}
