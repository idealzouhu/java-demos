package com.zouhu.proxy.pattern.staticProxy;

/**
 * @author zouhu
 * @data 2024-09-04 21:42
 */
public class Proxy implements Subject{
    private RealSubject realSubject;

    public Proxy() {
        this.realSubject = new RealSubject();
    }

    @Override
    public void request() {
        System.out.println("ProxySubject request");
        realSubject.request();
    }
}
