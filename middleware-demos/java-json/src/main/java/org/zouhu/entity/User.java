package org.zouhu.entity;

import lombok.Data;

/**
 * @author zouhu
 * @data 2024-11-08 19:58
 */
@Data
public class User {
    private long id;
    private String username;
    private String email;
    private int age;
    private boolean active;

    // Default constructor (required for Jackson)
    public User() {}

    // Parameterized constructor
    public User(long id, String username, String email, int age, boolean active) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;
        this.active = active;
    }
}
