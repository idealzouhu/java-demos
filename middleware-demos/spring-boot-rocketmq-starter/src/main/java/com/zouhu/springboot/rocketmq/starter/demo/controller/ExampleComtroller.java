package com.zouhu.springboot.rocketmq.starter.demo.controller;

import com.zouhu.springboot.rocketmq.starter.demo.mq.RocketMQProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试消息发送
 *
 * @author zouhu
 * @data 2024-09-19 14:39
 */
@RequiredArgsConstructor
@RestController
public class ExampleComtroller {

    private final RocketMQProducer rocketMQProducer;

    /**
     * 发送消息
     * <p>
     *     访问 http://localhost:9001/send?message=HelloRocketMQ 以发送消息
     *     访问 http://localhost:8088 查看控制台
     * <p>
     *     RocketMQConsumer Bean 会自动进行消费,并进行相关处理
     * </p>
     *
     * @param message
     * @return
     */
    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {

        rocketMQProducer.sendMessage("test-topic-1", message);
        return "Message sent!";
    }

}
