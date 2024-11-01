package com.zou.springboot.canal.redis.canal.service.impl;

import com.zou.springboot.canal.redis.canal.service.AbstractCanalRocketMqRedisService;
import com.zou.springboot.canal.redis.entity.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * User类的 Canal-RocketMQ通用处理服务实现
 *
 * @author zouhu
 * @data 2024-10-31 17:23
 */
@Component
public class UserCanalRocketMqRedisService extends AbstractCanalRocketMqRedisService<User> {
    public UserCanalRocketMqRedisService(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    protected String getModelName() {
        return "User";
    }
}
