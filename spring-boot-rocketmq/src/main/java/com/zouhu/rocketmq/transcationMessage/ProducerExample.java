package com.zouhu.rocketmq.transcationMessage;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientConfigurationBuilder;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.message.MessageBuilder;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.apache.rocketmq.client.apis.producer.Transaction;
import org.apache.rocketmq.client.apis.producer.TransactionResolution;
import org.apache.rocketmq.client.java.message.MessageBuilderImpl;
import org.apache.rocketmq.shaded.com.google.common.base.Strings;

import java.io.IOException;

/**
 * 生成事务消息
 * <p>
 *     创建 topic 命令：
 *     {@code $ sh mqadmin updatetopic -n rmqnamesrv:9876 -t TransactionTopic -c DefaultCluster -a +message.type=TRANSACTION }
 * <p>
 *     事务消息相比普通消息发送时需要修改以下几点：
 *     1. 发送事务消息前，需要开启事务并关联本地的事务执行。
 *     2. 为保证事务一致性，在构建生产者时，必须设置事务检查器和预绑定事务消息发送的主题列表，
 *     客户端内置的事务检查器会对绑定的事务主题做异常状态恢复。
 * </p>
 *
 * @author zouhu
 * @data 2024-09-03 17:02
 */
@Slf4j
public class ProducerExample {
    /**
     * 演示demo，模拟订单表查询服务，用来确认订单事务是否提交成功。
     * @param orderId 订单ID
     * @return
     */
    private static boolean checkOrderById(String orderId) {
        return true;
    }

    /**
     * 演示demo，模拟本地事务的执行结果。
     */
    private static boolean doLocalTransaction() {
        return true;
    }

    public static void main(String[] args) throws ClientException, IOException {
        // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8080;xxx:8081
        String endpoint = "localhost:8080";
        ClientConfiguration configuration =  ClientConfiguration.newBuilder()
                .setEndpoints(endpoint)
                .build();

        // 消息发送的目标Topic名称，需要提前创建。
        String topic = "TransactionTopic";

        //构造事务生产者：事务消息需要生产者构建一个事务检查器，用于检查确认异常半事务的中间状态。
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        Producer producer = provider.newProducerBuilder()
                .setClientConfiguration(configuration)
                .setTransactionChecker(messageView -> {
                    /**
                     * 事务检查器一般是根据业务的ID去检查本地事务是否正确提交还是回滚，此处以订单ID属性为例。
                     * 在订单表找到了这个订单，说明本地事务插入订单的操作已经正确提交；如果订单表没有订单，说明本地事务已经回滚。
                     */
                    final String orderId = messageView.getProperties().get("OrderId");
                    if (Strings.isNullOrEmpty(orderId)) {
                        // 错误的消息，直接返回Rollback。
                        return TransactionResolution.ROLLBACK;
                    }
                    return checkOrderById(orderId) ? TransactionResolution.COMMIT : TransactionResolution.ROLLBACK;
                })
                .build();

        // 开启事务分支
        final Transaction transaction;
        try {
            transaction = producer.beginTransaction();
        } catch (ClientException e) {
            e.printStackTrace();
            // 事务分支开启失败，直接退出
            return;
        }

        // 构建事务消息
        MessageBuilder messageBuilder = new MessageBuilderImpl();
        Message message = messageBuilder.setTopic(topic)
                // 设置消息索引键，可根据关键字精确查找某条消息
                .setKeys("transactionMessageKey")
                // 设置消息Tag，用于消费端根据指定Tag过滤消息
                .setTag("transactionMessageTag")
                // 一般事务消息都会设置一个本地事务关联的唯一ID，用来做本地事务回查的校验。
                .addProperty("OrderId", "xxx")
                // 消息体
                .setBody("transactionMessageBody".getBytes())
                .build();

        /**
         *   二阶段提交算法第一阶段：发送半事务消息给 RocketMQ 服务端。
         *   Apache RocketMQ服务端将消息持久化成功之后，向生产者返回Ack确认消息已经发送成功，
         *   此时消息被标记为"暂不能投递"，这种状态下的消息即为半事务消息。
         */
        final SendReceipt sendReceipt;
        try {
            sendReceipt = producer.send(message, transaction);
            log.info("Send half transaction message successfully, messageId = {}", sendReceipt.getMessageId());
        } catch (ClientException e) {
            // 半事务消息发送失败，事务可以直接退出并回滚
            log.warn("Send half transaction message failed.");
            e.printStackTrace();
            return;
        }

        /**
         *  二阶段提交算法第二阶段：生产者执行本地事务，并确定本地事务结果。
         *  1. 如果本地事务提交成功，则提交消息事务。
         *  2. 如果本地事务提交失败，则回滚消息事务。
         *  3. 如果本地事务未知异常，则不处理，等待事务消息回查。
         *
         */
        boolean localTransactionOk = doLocalTransaction();
        if (localTransactionOk) {
            try {
                transaction.commit();
                log.info("Commit transaction message successfully, messageId = {}", sendReceipt.getMessageId());
            } catch (ClientException e) {
                // 业务可以自身对实时性的要求选择是否重试，如果放弃重试，可以依赖事务消息回查机制进行事务状态的提交。
                e.printStackTrace();
            }
        } else {
            try {
                transaction.rollback();
                log.info("Rollback transaction message successfully, messageId = {}", sendReceipt.getMessageId());
            } catch (ClientException e) {
                // 建议记录异常信息，回滚异常时可以无需重试，依赖事务消息回查机制进行事务状态的提交。
                e.printStackTrace();
            }
        }

        producer.close();
    }
}
