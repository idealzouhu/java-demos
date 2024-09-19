package com.zouhu.springboot.idempotent.rocketmq.mq;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * 发送消息
 * <p>
 *     创建 topic 命令：
 *   \\{@code $ sh mqadmin updatetopic -t IdempotentTopic -c DefaultCluster -a +message.type=NORMAL }
 * </p>
 *

 * @author zouhu
 * @data 2024-09-18 23:06
 */
@Component
@RequiredArgsConstructor
@Slf4j(topic = "RocketMQProducer")
public class RocketMQProducer {

    private final RocketMQTemplate rocketMQTemplate;
    public void sendMessage(String message) {

        Message<String> rocketMessage = MessageBuilder.withPayload(message).build();
        try {
            rocketMQTemplate.send("idempotent-topic", rocketMessage);
            log.info("[生产者] 发送结果: {}", message);
        } catch (Exception e) {
            log.error("[生产者] 消息发送失败, 消息体:{}", message);
        }

    }
}
