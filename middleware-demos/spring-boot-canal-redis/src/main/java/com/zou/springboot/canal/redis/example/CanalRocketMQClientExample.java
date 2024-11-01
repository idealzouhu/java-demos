package com.zou.springboot.canal.redis.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.alibaba.otter.canal.client.rocketmq.RocketMQCanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import org.springframework.util.CollectionUtils;

/**
 * 测试 RocketMQCanalConnector 的使用
 * <p>
 *     1. start() 启动一个线程，用于消费消息
 *     2. stop() 停止消费
 *     3. process() 消费消息
 *
 * </p>
 *
 * @author zouhu
 * @data 2024-10-29 18:11
 */
@Slf4j
public class CanalRocketMQClientExample {
    private RocketMQCanalConnector          connector;

    private static volatile boolean         running = false;

    private Thread                          thread  = null;

    BaseCanalClientTest baseCanalClientTest;

    private Thread.UncaughtExceptionHandler handler = (t, e) -> log.error("parse events has an error", e);

    public CanalRocketMQClientExample(String nameServers, String topic, String groupId){
        connector = new RocketMQCanalConnector(nameServers, topic, groupId, 500, false);
    }

    public CanalRocketMQClientExample(String nameServers, String topic, String groupId, boolean enableMessageTrace,
                                      String accessKey, String secretKey, String accessChannel, String namespace){
        connector = new RocketMQCanalConnector(nameServers,
                topic,
                groupId,
                accessKey,
                secretKey,
                -1,
                false,
                enableMessageTrace,
                null,
                accessChannel,
                namespace);
    }

    public static void main(String[] args) {
        String nameServers = "127.0.0.1:9876";
        String topic = "canal-test-topic";
        String groupId = "my-producer_canal-test-topic";
        try {
            final CanalRocketMQClientExample rocketMQClientExample = new CanalRocketMQClientExample(nameServers,
                    topic,
                    groupId);
            log.info("## Start the rocketmq consumer: {}-{}", topic, groupId);
            rocketMQClientExample.start();
            log.info("## The canal rocketmq consumer is running now ......");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    log.info("## Stop the rocketmq consumer");
                    rocketMQClientExample.stop();
                } catch (Throwable e) {
                    log.warn("## Something goes wrong when stopping rocketmq consumer:", e);
                } finally {
                    log.info("## Rocketmq consumer is down.");
                }
            }));
            while (running)
                ;
        } catch (Throwable e) {
            log.error("## Something going wrong when starting up the rocketmq consumer:", e);
            System.exit(0);
        }
    }

    /**
     * 启动消费线程
     */
    public void start() {
        Assert.notNull(connector, "connector is null");
        thread = new Thread(this::process);
        thread.setUncaughtExceptionHandler(handler);
        thread.start();
        running = true;
    }

    /**
     * 停止消费
     */
    public void stop() {
        if (!running) {
            return;
        }
        running = false;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    /**
     * 消费消息的核心逻辑
     * <p>
     *    该方法首先等待一个运行信号，然后在运行时循环连接、订阅并处理消息
     *    当不再运行时，取消订阅并停止连接器
     * </p>
     */
    private void process() {
        // 等待运行信号
        while (!running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        // 循环消费消息
        while (running) {
            try {
                // 连接并订阅
                connector.connect();
                connector.subscribe();

                // 持续获取并处理消息
                while (running) {
                    List<Message> messages = connector.getListWithoutAck(1000L, TimeUnit.MILLISECONDS); // 获取message
                    for (Message message : messages) {
                        long batchId = message.getId();
                        int size = message.getEntries().size();

                        // 如果消息批次ID为-1或消息为空，则跳过处理
                        if (batchId == -1 || size == 0) {
                            // try {
                            // Thread.sleep(1000);
                            // } catch (InterruptedException e) {
                            // }
                        } else {
                            baseCanalClientTest.printSummary(message, batchId, size);
                            baseCanalClientTest.printEntry(message.getEntries());
                            // log.info(message.toString());
                        }
                    }

                    connector.ack(); // 提交确认
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        // 不再运行时，取消订阅
        connector.unsubscribe();
        // connector.stopRunning();
    }

}
