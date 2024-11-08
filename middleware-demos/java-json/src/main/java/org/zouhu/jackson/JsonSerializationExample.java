package org.zouhu.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.zouhu.entity.User;

/**
 * Jackson JSON序列化示例
 *
 * @author zouhu
 * @data 2024-11-08 19:59
 */
@Slf4j
public class JsonSerializationExample {
    public static void main(String[] args) {
        // 创建一个User对象
        User user = new User(1, "john_doe", "john@example.com", 30, true);

        // 创建ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // 将 User 对象转换为 JSON 字符串
            String jsonString = objectMapper.writeValueAsString(user);
            log.info("JSON String: {}", jsonString);
        } catch (JsonProcessingException ex) {
            log.error("Failed to convert User to JSON", ex);
            throw new RuntimeException(ex); // 可以选择重新抛出异常
        } catch (Exception ex) {
            log.error("Unexpected error occurred", ex);
        }

    }

}
