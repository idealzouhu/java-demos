package com.zouhu.notifyservice.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zouhu
 * @data 2024-09-09 15:35
 */
@FeignClient(name = "user-service")      // 定义一个 Feign 客户端接口，用于远程调用其他服务
public interface RemoteUserService {

    @GetMapping("/user/info")
    String getUserInfo();
}
