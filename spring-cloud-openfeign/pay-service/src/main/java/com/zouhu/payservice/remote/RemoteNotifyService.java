package com.zouhu.payservice.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "notify-service")
public interface RemoteNotifyService {

    @GetMapping("/notify/send")
    void sendNotification(@RequestParam("message") String message);
}
