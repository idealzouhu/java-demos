package com.zouhu.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Comparator 排序
 *
 * @author zouhu
 * @data 2024-08-27 21:10
 */
public class ComparatorExample {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        list.sort((o1, o2) -> o2 - o1);


        Integer[] numbers = {5, 3, 8, 1, 2};

        // 自定义 Comparator 进行降序排序
        Arrays.sort(numbers, Comparator.reverseOrder());

    }
}
