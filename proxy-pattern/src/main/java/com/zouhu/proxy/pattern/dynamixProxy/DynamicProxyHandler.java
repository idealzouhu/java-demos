package com.zouhu.proxy.pattern.dynamixProxy;

import java.lang.reflect.InvocationHandler;

/**
 * 动态代理的调用处理器
 *
 * @author zouhu
 * @data 2024-09-04 21:11
 */
public class DynamicProxyHandler implements InvocationHandler {
    private Subject subject;

    public DynamicProxyHandler(Subject subject) {
        this.subject = subject;
    }

    /**
     * 处理代理实例上的方法调用并返回结果。
     * <p>
     *     invoke方法是接口 InvocationHandler 定义必须实现的，它完成对真实方法的调用,
     *     调用真实对象的方法，即 {@code method.invoke(subject, args)}
     * </p>
     *
     * @param proxy 被调用方法的代理实例
     * @param method 与代理实例上被调用接口方法对应的 {@code Method} 实例。该方法声明的类是最初声明此方法的接口，
     *             可能是代理接口通过继承获得该方法的父接口。
     * @param args 包含方法调用时传递的参数值的对象数组，若接口方法无参数，则为 {@code null}。
     *            基本类型的参数会被包装成对应的包装类类型，如 {@code java.lang.Integer} 或 {@code java.lang.Boolean}。
     *
     * @return 处理结果
     * @throws Throwable 可能抛出的异常
     */
    @Override
    public Object invoke(Object proxy, java.lang.reflect.Method method, Object[] args) throws Throwable {
        System.out.println("before invoke");
        Object result = method.invoke(subject, args);
        System.out.println("after invoke");
        return result;
    }
}
