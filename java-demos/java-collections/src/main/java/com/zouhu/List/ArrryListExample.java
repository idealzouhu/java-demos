package com.zouhu.List;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zouhu
 * @data 2024-08-22 14:43
 */
public class ArrryListExample {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(10); // 初始容量为 10

        for (int i = 0; i < 12; i++) {
            list.add(i);
            System.out.println("Added: " + i + ", Size: " + list.size());
        }

    }
}
