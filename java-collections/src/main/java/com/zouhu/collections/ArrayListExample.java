package com.zouhu.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zouhu
 * @data 2024-08-14 21:24
 */
public class ArrayListExample {

    /**
     * 创建可变列表
     */
    public void createList() {
        // 创建不可变列表，报错
        // List<String> list = List.of("a", "b", "c");
        // list.add("d");

        // 将不可变列表的内容复制到一个新的可变列表中
        // List<String> list = new ArrayList<>(List.of("a", "b", "c"));
        // list.add("d");

        // 创建可变列表
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        list.add("d");
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        list.add("d");

    }
}
