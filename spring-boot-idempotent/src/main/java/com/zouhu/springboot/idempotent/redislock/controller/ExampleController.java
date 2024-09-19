package com.zouhu.springboot.idempotent.redislock.controller;

import com.zouhu.springboot.idempotent.redislock.annotation.Idempotent;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouhu
 * @data 2024-09-18 20:41
 */
@RestController
public class ExampleController {
    @PostMapping("/submit")
    @Idempotent(message = "请勿重复提交订单")
    public String submitOrder(@RequestBody Order order) {
        // 假设 Order 是一个实体类，包含了订单信息
        System.out.println("处理订单：" + order);
        return "订单提交成功：" + order.getId();
    }

    // 假设的Order实体类
    @Data
    @RequiredArgsConstructor
    public static class Order {
        private String id;
        private String details;

        // 构造器、getter和setter省略
    }
}
