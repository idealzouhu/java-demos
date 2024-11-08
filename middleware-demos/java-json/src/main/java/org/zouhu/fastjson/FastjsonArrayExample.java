package org.zouhu.fastjson;

import com.alibaba.fastjson2.JSON;
import org.zouhu.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Fastjson 序列化和反序列化 JSON 数组
 *
 * @author zouhu
 * @data 2024-11-08 20:36
 */
public class FastjsonArrayExample {
    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "alice", "alice@example.com", 28, true));
        userList.add(new User(2, "bob", "bob@example.com", 32, false));

        // 序列化 List<User> 为 JSON 数组
        String jsonArray = JSON.toJSONString(userList);
        System.out.println("JSON 数组: " + jsonArray);

        // 反序列化 JSON 数组为 List<User>
        List<User> deserializedList = JSON.parseArray(jsonArray, User.class);
        System.out.println("反序列化得到的列表: " + deserializedList);
    }
}
