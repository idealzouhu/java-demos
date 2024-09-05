package com.zouhu.proxy.pattern.dynamixProxy;

/**
 * 动态代理的抽象主题
 *
 * @author zouhu
 * @data 2024-09-04 21:06
 */
public interface Subject {
    // 真实主题和抽象主题共有的方法，即代理方法
    void request();
}
