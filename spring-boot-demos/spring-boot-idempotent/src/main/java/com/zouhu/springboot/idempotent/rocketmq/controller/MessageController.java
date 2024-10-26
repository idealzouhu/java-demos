package com.zouhu.springboot.idempotent.rocketmq.controller;

import com.zouhu.springboot.idempotent.rocketmq.mq.RocketMQProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试消费的的幂等性
 *
 * @author zouhu
 * @data 2024-09-19 13:18
 */
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final RocketMQProducer rocketMQProducer;

    /**
     * 访问 http://localhost:9001/send?message=HelloRocketMQ 以发送消息
     * 访问 http://localhost:8088/#/ 查看控制台
     *
     * @param message
     * @return
     */
    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        rocketMQProducer.sendMessage(message);
        return "消息发送成功: " + message;
    }

}
