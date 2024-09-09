package com.zouhu.notifyservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouhu
 * @data 2024-09-09 15:33
 */
@RestController
public class NotifyController {
    @GetMapping("/notify/send")
    void sendNotification(@RequestParam String message){
        System.out.println("Notification sent: " + message);
    }
}
