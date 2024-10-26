package com.zouhu.springboot.idempotent.redislock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc // 启用 MockMvc
class ExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void submitOrder() throws Exception {
        // 创建订单对象
        ExampleController.Order order = new ExampleController.Order();
        order.setId("12345");
        order.setDetails("测试订单详情");

        // 序列化订单对象为JSON字符串
        String orderJson = objectMapper.writeValueAsString(order);

        // 使用CountDownLatch来同步线程
        CountDownLatch latch = new CountDownLatch(3);

        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // 提交3个相同的任务到线程池, 模拟用户快速点击按钮功能
        for (int i = 0; i < 3; i++) {
            executorService.submit(() -> {
                try {
                    mockMvc.perform(MockMvcRequestBuilders.post("/submit")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(orderJson))
                            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                            .andExpect(MockMvcResultMatchers.content().string("订单提交成功：12345"));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        // 等待所有线程执行完毕
        latch.await();

        // 关闭线程池
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(1000);
        }
    }

}