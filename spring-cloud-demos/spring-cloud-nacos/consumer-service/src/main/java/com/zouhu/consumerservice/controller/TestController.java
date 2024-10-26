package com.zouhu.consumerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author zouhu
 * @data 2024-09-09 13:36
 */
@RestController
@EnableDiscoveryClient
public class TestController {


    private final RestTemplate restTemplate;

    @Autowired
    public TestController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

    /**
     * 测试是否能够使用 nacos 的服务发现功能
     * <p>
     *     调用 provider-service 的 /echo 接口，间接调用 provider-service 的接口
     *     测试 URL 为 http://localhost:9001/echo/2018
     *     http://10.8.12.174:9000/echo/2018
     * </p>
     *
     * @param str
     * @return
     */
    @GetMapping("/echo/{str}")
    public String echo(@PathVariable String str) {
        return restTemplate.getForObject("http://provider-service/echo/" + str, String.class);
    }

}
