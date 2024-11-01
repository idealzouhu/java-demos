package com.zou.springboot.canal.redis.mq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 简单地测试消息队列是否在正常工作，从而判断 canal 是否有问题
 *
 */
@SpringBootTest
class RocketMQProducerTest {

    @Autowired
    private RocketMQProducer rocketMQProducer;

    @Test
    void send() {
        String topic = "canal-test-topic";
        String msg = "hello world";

        rocketMQProducer.send(topic, msg);
    }
}