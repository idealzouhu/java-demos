package com.zouhu.rocketmq.normalMessage;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientConfigurationBuilder;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.message.MessageBuilder;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.apache.rocketmq.client.java.message.MessageBuilderImpl;

import java.io.IOException;

/**
 * 生成普通消息
 * <p>
 *     创建 topic 命令：
 *     {@code $ sh mqadmin updatetopic -t NormalTopic -c DefaultCluster -a +message.type=NORMAL }
 * </p>
 *
 * @author zouhu
 * @data 2024-09-03 17:02
 */
@Slf4j
public class ProducerExample {
    public static void main(String[] args) throws ClientException, IOException {
        // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8080;xxx:8081
        String endpoint = "localhost:8080";
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfigurationBuilder builder = ClientConfiguration.newBuilder().setEndpoints(endpoint);
        ClientConfiguration configuration = builder.build();

        // 消息发送的目标Topic名称，需要提前创建
        String topic = "NormalTopic";

        // 创建生产者，初始化Producer时需要设置通信配置以及预绑定的Topic
        Producer producer = provider.newProducerBuilder()
                .setTopics(topic)
                .setClientConfiguration(configuration)
                .build();

        // 发送普通消息
        MessageBuilder messageBuilder = new MessageBuilderImpl();
        Message message = messageBuilder.setTopic(topic)
                //设置消息索引键，可根据关键字精确查找某条消息
                .setKeys("normalMessageKey")
                //设置消息Tag，用于消费端根据指定Tag过滤消息
                .setTag("normalMessageTag")
                //消息体
                .setBody("normalMessageBody4".getBytes())
                .build();
        try {
            //发送消息，需要关注发送结果，并捕获失败等异常。
            SendReceipt sendReceipt = producer.send(message);
            log.info("Send message successfully, messageId = {}", sendReceipt.getMessageId());
        } catch (ClientException e) {
            log.error("Failed to send message");
            e.printStackTrace();
        }

        producer.close();
    }
}
