package com.zouhu;

import java.util.Optional;

/**
 * @author zouhu
 * @data 2024-11-11 15:59
 */
public class OptionalExample {
    public static void main(String[] args) {
        // 假设我们有不同类型的对象
        String errorMessage = "An error occurred!";
        String nullMessage = null;

        // 使用 Optional 处理 errorMessage，获取默认消息
        String resultMessage = Optional.ofNullable(errorMessage)
                .orElse("No error message available");

        // 使用 Optional 处理 nullMessage，获取默认消息
        String nullResultMessage = Optional.ofNullable(nullMessage)
                .orElse("No error message available");

        // 打印结果
        System.out.println("Result Message: " + resultMessage);
        System.out.println("Null Result Message: " + nullResultMessage);
    }
}
