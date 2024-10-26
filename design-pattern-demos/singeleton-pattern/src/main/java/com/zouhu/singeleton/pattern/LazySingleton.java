package com.zouhu.singeleton.pattern;

/**
 * 懒汉式单例模式
 *
 * @author zouhu
 * @data 2024-09-04 16:36
 */
public class LazySingleton {
    // 静态成员变量，保存单例对象
    private static LazySingleton instance;

    // 私有构造方法，防止外部实例化
    private LazySingleton() {}

    // 公共静态方法，返回单例对象
    public static LazySingleton getInstance() {
        // 双重检查加锁，线程安全
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }

    // 示例方法，用于演示单例的行为
    public void doSomething() {
        System.out.println("Doing something...");
    }

    public static void main(String[] args) {
        // 获取单例对象并调用方法
        LazySingleton singleton = LazySingleton.getInstance();
        singleton.doSomething();
    }
}
