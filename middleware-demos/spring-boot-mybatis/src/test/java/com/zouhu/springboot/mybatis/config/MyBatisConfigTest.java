package com.zouhu.springboot.mybatis.config;

import com.zouhu.springboot.mybatis.entity.User;
import com.zouhu.springboot.mybatis.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyBatisConfigTest {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void test() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> users = mapper.selectAll();
            for (User user : users) {
                System.out.println(user);
            }
        }
    }

}