package com.zouhu.rocketmq.delayMessage;

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

import java.util.Date;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 生成普通消息
 * <p>
 *     创建 topic 命令：
 *     {@code sh mqadmin updatetopic  -n rmqnamesrv:9876 -t DelayTopic -c DefaultCluster -a +message.type=DELAY }
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

        // 消息发送的目标Topic名称，需要提前创建。
        String topic = "DelayTopic";

        // 创建生产者，初始化Producer时需要设置通信配置以及预绑定的Topic
        Producer producer = provider.newProducerBuilder()
                .setTopics(topic)
                .setClientConfiguration(configuration)
                .build();

        //发送普通消息
        MessageBuilder messageBuilder = new MessageBuilderImpl();
        // 延迟时间为 1 分钟之后的Unix时间戳
        Long deliverTimeStamp = System.currentTimeMillis() + 1L * 60 * 1000;
        Message message = messageBuilder.setTopic(topic)
                //设置消息索引键，可根据关键字精确查找某条消息
                .setKeys("delayMessageKey")
                //设置消息Tag，用于消费端根据指定Tag过滤消息
                .setTag("delayMessageTag")
                // 设置消息的定时时间戳，单位毫秒(发送定时消息的相关代码)
                .setDeliveryTimestamp(deliverTimeStamp)
                //消息体
                .setBody("delayMessageBody2".getBytes())
                .build();
        try {
            // 预计到达时间（不考虑传输时间）
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String expectedReceiveTimeStr = sdf.format(new Date(deliverTimeStamp));
            log.info("Estimated arrival time: {}", expectedReceiveTimeStr);

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
