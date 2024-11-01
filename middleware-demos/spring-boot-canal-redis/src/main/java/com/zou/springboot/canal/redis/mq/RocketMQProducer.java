package com.zou.springboot.canal.redis.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author zouhu
 * @data 2024-10-29 22:20
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RocketMQProducer {

    private final RocketMQTemplate rocketMQTemplate;


    public void send(String topic, String msg) {
        try {
            rocketMQTemplate.convertAndSend(topic, msg);
            log.info("Message sent to topic {}: {}", topic, msg);
        } catch (Exception e) {
            // 记录日志
            log.error("Failed to send message to topic {}: {}", topic, e.getMessage(), e);
            // 可以选择重试机制或其他处理方式
        }
    }
}
