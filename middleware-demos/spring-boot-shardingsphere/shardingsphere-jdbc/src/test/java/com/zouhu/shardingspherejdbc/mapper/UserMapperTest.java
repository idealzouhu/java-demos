package com.zouhu.shardingspherejdbc.mapper;

import com.zouhu.shardingspherejdbc.entity.User;
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
        int userID = 28;
        User user = new User(userID, "testUser28");
        userMapper.insertUser(user);

        // 查询插入的数据
        // User fetchedUser = userMapper.getUserByUserId(userID);
        // assertNotNull(fetchedUser);
        // assertEquals("testUser", fetchedUser.getUsername());
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