package com.zouhu.proxy.pattern.dynamixProxy;

/**
 * 动态代理的真实主题
 *
 * @author zouhu
 * @data 2024-09-04 21:07
 */
public class RealSubject implements Subject{
    @Override
    public void request() {
        System.out.println("RealSubject request");
    }
}
