package com.zouhu.mybatis.client.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

/**
 *  得到 sql 语句的执行对象
 *  <p>
 *      可以类比JDBC中的connection和statement
 *  <p>
 *      <a href="https://mybatis.net.cn/getting-started.html">从XML 中构建 SqlSessionFactory 的教程链接</a>
 *  </p>
 *
 * @author zouhu
 * @data 2024-08-13 22:22
 */
@Configuration
public class MyBatisConfig {
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        String resource = "mybatis-config.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            System.out.println("Resource loaded successfully.");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            return sqlSessionFactory;
        } catch (Exception e) {
            System.err.println("Failed to create SqlSessionFactory: " + e.getMessage());
            throw e;
        }
    }
}
