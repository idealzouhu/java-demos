package com.zouhu.builder.pattern.lombok;

/**
 * 测试建造者模式
 *
 * @author zouhu
 * @data 2024-09-04 17:43
 */
public class BuilderPatternExample {
    public static void main(String[] args) {
        User user = User.builder()
                .userId("123456")
                .username("zouhu")
                .build();
        System.out.println(user);
    }
}
