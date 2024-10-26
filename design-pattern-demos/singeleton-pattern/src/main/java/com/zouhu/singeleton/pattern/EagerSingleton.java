package com.zouhu.singeleton.pattern;

/**
 * 饿汉式单例模式
 *
 * @author zouhu
 * @data 2024-09-04 16:32
 */
public class EagerSingleton {
    // 静态成员变量，保存单例对象（final 使对象具备不可变性，从而保证线程安全）
    private static EagerSingleton instance = new EagerSingleton();

    // 私有构造方法，防止外部实例化
    private EagerSingleton() {}

    // 公共静态方法，返回单例对象
    public static EagerSingleton getInstance() {
        return instance;
    }

    // 示例方法，用于演示单例的行为
    public void doSomething() {
        System.out.println("do something");
    }

    public static void main(String[] args) {
        // 获取单例对象并调用方法
        EagerSingleton eagerSingleton = EagerSingleton.getInstance();
        eagerSingleton.doSomething();
    }
}
