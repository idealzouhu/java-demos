package com.zouhu.rocketmq.demo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.*;
import org.apache.rocketmq.client.apis.message.MessageView;


/**
 * 订阅普通消息
 *
 * @author zouhu
 * @data 2024-08-12 20:07
 */
@Slf4j
public class ConsumerExample {
    public static void main(String[] args) throws ClientException, IOException, InterruptedException {
        final ClientServiceProvider provider = ClientServiceProvider.loadService();
        // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8080;xxx:8081
        // 此处为示例，实际使用时请替换为真实的 Proxy 地址和端口
        String endpoints = "localhost:8080";
        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                .setEndpoints(endpoints)
                .build();
        // 订阅消息的过滤规则，表示订阅所有Tag的消息
        String tag = "*";
        FilterExpression filterExpression = new FilterExpression(tag, FilterExpressionType.TAG);
        // 为消费者指定所属的消费者分组，Group需要提前创建
        String consumerGroup = "YourConsumerGroup";
        // 指定需要订阅哪个目标Topic，Topic需要提前创建。
        String topic = "TestTopic";

        // 消费示例一：使用 PushConsumer 消费定时消息，需要绑定消费者分组ConsumerGroup、通信参数以及订阅关系
        // PushConsumer pushConsumer = provider.newPushConsumerBuilder()
        //         .setClientConfiguration(clientConfiguration)
        //         // 设置消费者分组
        //         .setConsumerGroup(consumerGroup)
        //         // 设置预绑定的订阅关系
        //         .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
        //         // 设置消费监听器
        //         .setMessageListener(messageView -> {
        //             // 分析消息内容
        //             ByteBuffer bodyBuffer = messageView.getBody();
        //             byte[] bodyBytes = new byte[bodyBuffer.remaining()];
        //             bodyBuffer.get(bodyBytes); // 读取缓冲区中的字节
        //             String messageBody = new String(bodyBytes);
        //
        //             // 处理消息并返回消费结果
        //             // log.info(messageView.toString());
        //             log.info("Consume message successfully, messageId={}, messageBody={},", messageView.getMessageId(), messageBody);
        //             return ConsumeResult.SUCCESS;
        //         })
        //         .build();

        //消费示例二：使用SimpleConsumer消费定时消息，主动获取消息进行消费处理并提交消费结果。
        SimpleConsumer simpleConsumer = provider.newSimpleConsumerBuilder()
                // 设置接入点。
                .setClientConfiguration(clientConfiguration)
                // 设置消费者分组。
                .setConsumerGroup(consumerGroup)
                // 设置预绑定的订阅关系。
                .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
                // 设置从服务端接受消息的最大等待时间
                .setAwaitDuration(Duration.ofSeconds(10))
                .build();
        List<MessageView> messageViewList = null;
        try {
            // receive方法等待最多30秒，期望从消息队列中接收最多10条消息
            messageViewList = simpleConsumer.receive(10, Duration.ofSeconds(30));
            messageViewList.forEach(messageView -> {
                // 分析消息内容
                ByteBuffer bodyBuffer = messageView.getBody();
                byte[] bodyBytes = new byte[bodyBuffer.remaining()];
                bodyBuffer.get(bodyBytes); // 读取缓冲区中的字节
                String messageBody = new String(bodyBytes);
                // log.info(messageView.toString());
                log.info("Consume message successfully, messageId={}, messageBody={},", messageView.getMessageId(), messageBody);

                //消费处理完成后，需要主动调用ACK提交消费结果。
                try {
                    simpleConsumer.ack(messageView);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            });
        } catch (ClientException e) {
            //如果遇到系统流控等原因造成拉取失败，需要重新发起获取消息请求。
            e.printStackTrace();
        }

        // 如果不需要再使用 PushConsumer，可关闭该实例
        // Thread.sleep(Long.MAX_VALUE);
        // pushConsumer.close();
        // simpleConsumer.close();
    }
}
