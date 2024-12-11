package com.zouhu.mybatis.client.mapper;

import com.zouhu.mybatis.client.dto.UserOrderDTO;
import com.zouhu.mybatis.client.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void selectAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> users = mapper.selectAll();
            for (User user : users) {
                System.out.println(user);
            }
        }
    }

    @Test
    void selectById() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.selectById(1L);
            System.out.println(user);
        }
    }

    @Test
    void insert() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = new User();
            user.setId(6L);
            user.setName("测试");
            user.setPhone("12345678901");
            user.setSex("男");
            user.setStatus(1);
            mapper.insertUser(user);
            // 提交当前会话中的所有数据库更改到实际数据库
            session.commit();
        }
    }

    @Test
    void updateUser() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = new User();
            user.setId(6L);
            user.setName("测试");
            user.setPhone("12345678901");
            user.setSex("女");
            user.setStatus(1);
            mapper.updateUser(user);
            // 提交事务
            session.commit();
        }
    }

    @Test
    void deleteById() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.deleteById(6L);
            session.commit();
        }
    }

    @Test
    void selectUsersWithOrderNumbers() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<UserOrderDTO> userOrders = mapper.selectUsersWithOrderNumbers();
            for (UserOrderDTO userOrder : userOrders) {
                System.out.println("User: " + userOrder.getUserName() + ", Order Number: " + userOrder.getOrderNumber());
            }
        }
    }

    @Test
    void selectUsersByCondition() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);

            // 仅通过状态筛选用户
            Map<String, Object> params1 = new HashMap<>();
            params1.put("status", 1);
            List<User> users1 = mapper.selectUsersByCondition(params1);
            for (User user : users1) {
                System.out.println(user);
            }
            session.commit();

            // 通过姓名、性别和状态筛选用户
            Map<String, Object> params2 = new HashMap<>();
            params2.put("name", "测试");
            params2.put("sex", "男");
            params2.put("status", 1);
            List<User> users2 = mapper.selectUsersByCondition(params2);
            for (User user : users2) {
                System.out.println(user);
            }
            session.commit();
        }
    }
}