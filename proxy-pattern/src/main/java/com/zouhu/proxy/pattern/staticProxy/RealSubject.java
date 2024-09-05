package com.zouhu.proxy.pattern.staticProxy;

/**
 * 真实主题
 *
 * @author zouhu
 * @data 2024-09-04 21:41
 */
public class RealSubject implements Subject{
    @Override
    public void request() {
        System.out.println("RealSubject request");
    }
}
