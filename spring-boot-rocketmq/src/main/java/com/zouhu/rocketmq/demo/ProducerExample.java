package com.zouhu.rocketmq.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientConfigurationBuilder;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;

import java.io.IOException;

/**
 * 发送普通消息示例
 *
 * @author zouhu
 * @data 2024-08-12 20:05
 */
@Slf4j
public class ProducerExample {
    public static void main(String[] args) throws ClientException, IOException {
        // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8080;xxx:8081
        String endpoint = "localhost:8080";

        // 消息发送的目标Topic名称，需要提前创建。
        String topic = "TestTopic";
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfigurationBuilder builder = ClientConfiguration.newBuilder().setEndpoints(endpoint);
        ClientConfiguration configuration = builder.build();

        // 初始化Producer时需要设置通信配置以及预绑定的Topic。
        Producer producer = provider.newProducerBuilder()
                .setTopics(topic)
                .setClientConfiguration(configuration)
                .build();

        // 普通消息发送。
        Message message = provider.newMessageBuilder()
                .setTopic(topic)
                // 设置消息索引键，可根据关键字精确查找某条消息(可选)。
                .setKeys("messageKey")
                // 设置消息Tag，用于消费端根据指定Tag过滤消息(可选)。
                .setTag("messageTag")
                // 消息体。
                .setBody("messageBody5".getBytes())
                .build();
        try {
            // 发送消息，需要关注发送结果，并捕获失败等异常。
            SendReceipt sendReceipt = producer.send(message);
            log.info("Send message successfully, messageId={}", sendReceipt.getMessageId());
        } catch (ClientException e) {
            log.error("Failed to send message");
            e.printStackTrace();
        }
        producer.close();
    }
}
