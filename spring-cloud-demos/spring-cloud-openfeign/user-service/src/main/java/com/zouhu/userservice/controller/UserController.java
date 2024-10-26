package com.zouhu.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zouhu
 * @data 2024-09-06 19:46
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/info")
    public String getUser(){
        return  "User service is up and running!";
    }
}
