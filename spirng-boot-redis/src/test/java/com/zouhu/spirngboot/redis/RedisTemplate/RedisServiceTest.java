package com.zouhu.spirngboot.redis.RedisTemplate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisServiceTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisService redisService;

    @BeforeEach
    void setUp() {
        // 清理 Redis 中的所有数据
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @AfterEach
    void tearDown() {
        // 清理 Redis 中的所有数据
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @Test
    void saveString() {
        redisService.saveString("testKey", "testValue");
        String value = redisService.getString("testKey");
        System.out.println("testKey :" + value);
        assertEquals("testValue", value);
    }

    @Test
    void saveHash() {
        redisService.saveHash("testHash", "hashKey", "hashValue");
        String value = redisService.getHash("testHash", "hashKey");
        assertEquals("hashValue", value);
    }


    @Test
    void saveList() {
        redisService.saveList("testList", "listValue");
        String value = redisService.getList("testList", 0);
        assertEquals("listValue", value);
    }

    @Test
    void saveSet() {
//        redisService.saveSet("testSet", "setValue");
//        boolean isMember = redisService.isMemberOfSet("testSet", "setValue");
//        assertTrue(isMember);

        redisService.saveSet("testSet", "value1");
        redisService.saveSet("testSet", "value2");
        redisService.saveSet("testSet", "value3");

        Set<String> values = redisService.getSet("testSet");
        System.out.println("testSet: " + values);
        assertTrue(values.contains("value1"));
        assertTrue(values.contains("value2"));
        assertTrue(values.contains("value3"));
    }


    @Test
    void saveZSet() {
        redisService.saveZSet("testZSet", "zsetValue", 1.0);
        Set<Object> values = redisService.getZSetRange("testZSet", 0, 2);
        assertTrue(values.contains("zsetValue"));
    }

}