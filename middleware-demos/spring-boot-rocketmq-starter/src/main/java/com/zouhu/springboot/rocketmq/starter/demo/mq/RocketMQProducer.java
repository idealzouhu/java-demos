package com.zouhu.springboot.rocketmq.starter.demo.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 生成普通消息
 * <p>
 *     创建 topic 命令：
 *     {@code $ sh mqadmin updatetopic -t test-topic-1 -c DefaultCluster -a +message.type=NORMAL }
 * </p>
 *
 * @author zouhu
 * @data 2024-09-19 14:36
 */
@Component
@RequiredArgsConstructor
public class RocketMQProducer {

    private final RocketMQTemplate rocketMQTemplate;

    /**
     * 展示四种发送方式
     * <p>
     *     1. send message synchronously.    同步发送消息
     *     2. send spring message.           构建spring message后发送消息
     *     3. send message asynchronously.   异步发送消息
     *     4. Send messages orderly.         顺序发送消息
     * </p>
     *
     * @param topic
     * @param message
     */
    public void sendMessage(String topic, String message) {
        // 1. send message synchronously
        rocketMQTemplate.convertAndSend(topic, message);
        // rocketMQTemplate.convertAndSend(topic, new OrderPaidEvent("T_001", new BigDecimal("88.00")));

        // 2. send spring message
        // Message<String> rocketMessage = MessageBuilder.withPayload(message).build();
        // rocketMQTemplate.send(topic, rocketMessage);

        // 3. send message asynchronously
        // rocketMQTemplate.asyncSend(topic, new OrderPaidEvent("T_001", new BigDecimal("88.00")), new SendCallback() {
        //     // 发送成功
        //     @Override
        //     public void onSuccess(SendResult var1) {
        //         System.out.printf("async onSucess SendResult=%s %n", var1);
        //     }
        //
        //     // 发送失败
        //     @Override
        //     public void onException(Throwable var1) {
        //         System.out.printf("async onException Throwable=%s %n", var1);
        //     }
        //
        // });

        // 4. Send messages orderly
        // Message<String> rocketMessage = MessageBuilder.withPayload(message).build();
        // rocketMQTemplate.syncSendOrderly(topic, rocketMessage,"hashkey");

        System.out.println("Message sent: " + message);
    }

    @Data
    @AllArgsConstructor
    public class OrderPaidEvent implements Serializable {
        private String orderId;

        private BigDecimal paidMoney;
    }
}
