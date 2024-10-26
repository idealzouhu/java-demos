package com.zouhu.springboot.idempotent.rocketmq.mq;

import com.alibaba.fastjson.JSON;
import com.zouhu.springboot.idempotent.rocketmq.annotation.Idempotent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author zouhu
 * @data 2024-09-18 23:10
 */
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = "idempotent-topic",
        consumerGroup = "consumer-group_idempotent-topic"
)
@Slf4j(topic = "RocketMQConsumer")
public class RocketMQConsumer implements RocketMQListener<String> {

    @Idempotent(
            uniqueKeyPrefix =  "idempotent_test:",
            key = "#message",   // 这里最好还要加上用户 ID, 以保证唯一性
            keyTimeout = 120,
            message = "发送消息的操作过于频繁，请稍后再试"
    )
    @Override
    public void onMessage(String message) {
        try {
            // 你的消费逻辑
            log.info("[消费者] 执行消费逻辑，消息体：{}", JSON.toJSONString(message));
        } catch (Exception e) {
            log.error("[消费者] 消息处理失败, 消息体：{}", JSON.toJSONString(message), e);
        }
    }

}
