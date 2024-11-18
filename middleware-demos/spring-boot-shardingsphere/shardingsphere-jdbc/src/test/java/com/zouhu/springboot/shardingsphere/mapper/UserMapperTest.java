package com.zouhu.springboot.shardingsphere.mapper;

import com.zouhu.springboot.shardingsphere.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void insertUser() {
        // 插入数据
        User user = new User(1, "testUser");
        userMapper.insertUser(user);

        // 查询插入的数据
        User fetchedUser = userMapper.getUserByUserId(1);
        assertNotNull(fetchedUser);
        assertEquals("testUser", fetchedUser.getUsername());
    }

    @Test
    void getUserByOrderId() {
        User fetchedUser = userMapper.getUserByUserId(1);
        assertNotNull(fetchedUser);
        assertEquals("testUser", fetchedUser.getUsername());
    }

    @Test
    void getAllUsers() {
        List<User> users = userMapper.getAllUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }
}