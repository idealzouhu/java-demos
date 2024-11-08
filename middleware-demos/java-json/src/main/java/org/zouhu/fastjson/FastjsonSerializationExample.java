package org.zouhu.fastjson;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import org.zouhu.entity.User;

/**
 * Fsatson 序列化
 *
 * @author zouhu
 * @data 2024-11-08 20:33
 */
public class FastjsonSerializationExample {
    public static void main(String[] args) {
        // 创建 User 对象
        User user = new User(1, "john_doe", "john@example.com", 30, true);

        // 将 Java 对象转换为 JSON 字符串
        String jsonString = JSON.toJSONString(user);
        System.out.println("序列化后的 JSON 字符串: " + jsonString);

        // 使用 Pretty Format 序列化
        String prettyJson = JSON.toJSONString(user, JSONWriter.Feature.PrettyFormat);
        System.out.println("格式化后的 JSON 字符串:\n" + prettyJson);
    }
}
