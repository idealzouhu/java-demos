package com.zouhu.proxy.pattern.dynamixProxy;

import java.lang.reflect.Proxy;

/**
 * 测试动态代理
 *
 * @author zouhu
 * @data 2024-09-04 21:10
 */
public class DynamicProxyDemo {

    public static void main(String[] args) throws Exception {
        // 创建真实主题对象
        RealSubject realSubject = new RealSubject();

        // 创建动态代理对象 proxy
        Subject proxy = (Subject) Proxy.newProxyInstance(
                RealSubject.class.getClassLoader(),     // 指定类加载器，用于定义代理类
                new Class[]{Subject.class},             // 代理类需实现的接口列表
                new DynamicProxyHandler(realSubject)    // 处理代理方法调用的调用处理器（此处为realSubject的实际调用）
        );

        // 调用代理对象 proxy 的方法 request()
        // 当代理对象的一个方法被调用时，JVM 会捕获这个方法调用，并将其转发给 InvocationHandler 的 invoke 方法
        proxy.request();
    }
}
