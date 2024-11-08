package org.zouhu.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.zouhu.entity.User;

/**
 * Jackson 反序列化
 *
 * @author zouhu
 * @data 2024-11-08 20:24
 */
@Slf4j
public class JsonDeserializationExample {
    public static void main(String[] args) {
        // 定义一个 JSON 字符串
        String jsonString = "{\"id\":1,\"username\":\"john_doe\",\"email\":\"john@example.com\",\"age\":30,\"active\":true}";

        // 创建 ObjectMapper 对象
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // 将 JSON 字符串转换为 User 对象
            User user = objectMapper.readValue(jsonString, User.class);
            log.info("Deserialized User: {}", user);

            // 使用解析得到的User对象
            System.out.println("ID: " + user.getId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Age: " + user.getAge());
            System.out.println("Active: " + user.isActive());
        } catch (JsonProcessingException ex) {
            log.error("Failed to convert JSON to User", ex);
            throw new RuntimeException(ex); // 可以选择重新抛出异常
        } catch (Exception ex) {
            log.error("Unexpected error occurred", ex);
        }
    }
}
