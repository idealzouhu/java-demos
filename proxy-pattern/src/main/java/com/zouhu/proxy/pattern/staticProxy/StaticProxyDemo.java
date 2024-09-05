package com.zouhu.proxy.pattern.staticProxy;

/**
 * @author zouhu
 * @data 2024-09-04 21:43
 */
public class StaticProxyDemo {
    public static void main(String[] args) {
        Subject subject = new Proxy();
        subject.request();
    }
}
