package com.zouhu.mybatis.starter.mapper;

import com.zouhu.mybatis.starter.dto.UserOrderDTO;
import com.zouhu.mybatis.starter.entity.User;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void selectAll() {
        List<User> users = userMapper.selectAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    void selectById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    @Test
    void insert() {
            User user = new User();
            user.setId(7L);
            user.setName("测试");
            user.setPhone("12345678901");
            user.setSex("男");
            user.setStatus(1);
            userMapper.insertUser(user);
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setId(7L);
        user.setName("测试");
        user.setPhone("12345678901");
        user.setSex("女");
        user.setStatus(1);
        userMapper.updateUser(user);
    }

    @Test
    void deleteById() {
        userMapper.deleteById(7L);
    }

    @Test
    void selectUsersWithOrderNumbers() {
        List<UserOrderDTO> userOrders = userMapper.selectUsersWithOrderNumbers();
        for (UserOrderDTO userOrder : userOrders) {
            System.out.println("User: " + userOrder.getUserName() + ", Order Number: " + userOrder.getOrderNumber());
        }
    }

}