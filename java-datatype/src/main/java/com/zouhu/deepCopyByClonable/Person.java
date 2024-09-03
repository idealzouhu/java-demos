package com.zouhu.deepCopyByClonable;

import java.io.*;

/**
 * 用于测试深拷贝
 *
 * @author zouhu
 * @data 2024-08-27 16:09
 */
public class Person implements Cloneable  {
    private String name;
    private int age;
    private Address address;

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // 深拷贝
        Person cloned = (Person) super.clone();
        cloned.address = (Address) address.clone();  // 深拷贝地址对象
        return cloned;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
}
