package com.zouhu.copy.deepCopyBySerialization;

import java.io.Serializable;

/**
 * @author zouhu
 * @data 2024-08-27 16:10
 */
public class Address implements Serializable {
    private String city;
    private String street;

    public Address(String city, String street) {
        this.city = city;
        this.street = street;
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
