package com.zouhu;

/**
 * 验证包装类的缓存机制
 *
 * @author zouhu
 * @data 2024-08-27 15:08
 */
public class CacheMechanism {
    public static void main(String[] args) {
        // 测试Integer包装类缓存机制
        Integer a = 127;
        Integer b = 127;
        System.out.println(a == b); // 输出 true，引用相同，使用缓存

        // 测试Integer包装类缓存机制
        Integer c = 128;
        Integer d = 128;
        System.out.println(c == d); // 输出 false，超出缓存范围，创建新对象
    }
}
