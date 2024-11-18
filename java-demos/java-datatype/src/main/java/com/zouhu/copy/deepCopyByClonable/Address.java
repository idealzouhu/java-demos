package com.zouhu.copy.deepCopyByClonable;

import java.io.Serializable;

/**
 * @author zouhu
 * @data 2024-08-27 16:10
 */
public class Address implements Cloneable  {
    private String city;
    private String street;

    public Address(String city, String street) {
        this.city = city;
        this.street = street;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
