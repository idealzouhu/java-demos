package com.zouhu.payservice.controller;

import com.zouhu.payservice.remote.RemoteNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zouhu
 * @data 2024-09-06 21:05
 */
@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private RemoteNotifyService remotePayService;

    @GetMapping("/process")
    public String processPayment() {
        // Call Notify Service
        remotePayService.sendNotification("Payment processed successfully!");
        return "Payment processed!";
    }

}
