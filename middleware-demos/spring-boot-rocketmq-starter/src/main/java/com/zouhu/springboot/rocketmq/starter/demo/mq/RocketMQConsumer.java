package com.zouhu.springboot.rocketmq.starter.demo.mq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author zouhu
 * @data 2024-09-19 14:38
 */
@Component
@RocketMQMessageListener(
        topic = "test-topic-1",
        consumerGroup = "my-consumer_test-topic-1"
)
public class RocketMQConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
    }
}


// @RocketMQMessageListener(
//         topic = "test-topic-2",
//         consumerGroup = "my-consumer_test-topic-2"
// )
// public class RocketMQConsumer implements RocketMQListener<RocketMQProducer.OrderPaidEvent> {
//
//     @Override
//     public void onMessage(RocketMQProducer.OrderPaidEvent message) {
//         System.out.println("Received message: " + message);
//     }
// }
