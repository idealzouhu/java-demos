package com.zouhu.aop.log;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouhu
 * @data 2024-07-27 17:31
 */
@RestController
public class TestController {
    @ILog(input = true, output = true) // 应用 ILog 注解
    @GetMapping("/api/test") // 映射 GET 请求
    public String performAction(@RequestParam String input) {

        System.out.println("TestController.performAction() ");
        // 模拟业务逻辑
        return "Processed: " + input;
    }
}
