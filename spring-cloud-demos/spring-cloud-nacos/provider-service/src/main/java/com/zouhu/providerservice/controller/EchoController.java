package com.zouhu.providerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouhu
 * @data 2024-09-09 13:33
 */
@RestController
public class EchoController {

    /**
     * 测试 url： http://localhost:9000/echo/zouhu
     *
     * @param string
     * @return
     */
    @GetMapping("/echo/{string}")
    public String echo(@PathVariable String string) {
        return "Hello Nacos Discovery " + string;
    }
}
