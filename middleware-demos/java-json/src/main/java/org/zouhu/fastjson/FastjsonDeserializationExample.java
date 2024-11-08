package org.zouhu.fastjson;

import com.alibaba.fastjson2.JSON;
import org.zouhu.entity.User;

/**
 * Fastjson 反序列化
 *
 * @author zouhu
 * @data 2024-11-08 20:34
 */
public class FastjsonDeserializationExample {
    public static void main(String[] args) {
        // JSON 字符串
        String jsonString = "{\"id\":1,\"username\":\"john_doe\",\"email\":\"john@example.com\",\"age\":30,\"active\":true}";

        // 将 JSON 字符串反序列化为 User 对象
        User user = JSON.parseObject(jsonString, User.class);
        System.out.println("反序列化得到的 User 对象: ");
        System.out.println("ID: " + user.getId());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Age: " + user.getAge());
        System.out.println("Active: " + user.isActive());
    }
}
