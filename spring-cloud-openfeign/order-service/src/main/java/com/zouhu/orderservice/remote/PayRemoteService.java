package com.zouhu.orderservice.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "pay-service")      // 定义一个 Feign 客户端接口，用于远程调用其他服务
public interface PayRemoteService {

    // 定义远程调用方法
    @GetMapping("/pay/process")
    String processPayment();

}
