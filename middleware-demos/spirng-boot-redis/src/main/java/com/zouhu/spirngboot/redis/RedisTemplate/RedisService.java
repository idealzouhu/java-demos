package com.zouhu.spirngboot.redis.RedisTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 存储字符串
    public void saveString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 获取字符串
    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    // 存储哈希
    public void saveHash(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    // 获取哈希
    public String getHash(String key, String hashKey) {
        return (String) redisTemplate.opsForHash().get(key, hashKey);
    }

    // 存储列表
    public void saveList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    // 获取列表
    public String getList(String key, long index) {
        return (String) redisTemplate.opsForList().index(key, index);
    }

    // 存储集合
    public void saveSet(String key, String value) {
        stringRedisTemplate.opsForSet().add(key, value);
    }

    // 获取集合
    public Set<String> getSet(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    // 存储有序集合
    public void saveZSet(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    // 获取有序集合
    public Set<Object> getZSetRange(String key, double minScore, double maxScore) {
        return redisTemplate.opsForZSet().rangeByScore(key, minScore, maxScore);
    }
}

