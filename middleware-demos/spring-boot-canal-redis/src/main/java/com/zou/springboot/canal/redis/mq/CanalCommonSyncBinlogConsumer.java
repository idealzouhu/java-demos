package com.zou.springboot.canal.redis.mq;

import com.alibaba.otter.canal.protocol.FlatMessage;
import com.zou.springboot.canal.redis.canal.service.CanalSyncService;
import com.zou.springboot.canal.redis.canal.service.impl.UserCanalRocketMqRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 监听所有表的数据修改 binlog
 * <p>
 *     目前只实现了单个表的处理逻辑, 后续可以使用策略模式实现不同表的处理逻辑
 * </p>
 *
 * @author zouhu
 * @data 2024-10-27 23:18
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = "canal-test-topic",
        consumerGroup = "my-consumer_test-topic-1"
)
public class CanalCommonSyncBinlogConsumer implements RocketMQListener<FlatMessage> {

    private final UserCanalRocketMqRedisService userCanalRocketMqRedisService;

    @Override
    public void onMessage(FlatMessage flatMessage) {
        log.info("consumer message {}", flatMessage);
        try {
            userCanalRocketMqRedisService.process(flatMessage);
        } catch (Exception e) {
            log.warn(String.format("message [%s] 消费失败", flatMessage), e);
            throw new RuntimeException(e);
        }
    }
}
