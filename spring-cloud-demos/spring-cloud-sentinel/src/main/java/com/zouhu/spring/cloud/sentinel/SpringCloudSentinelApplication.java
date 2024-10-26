package com.zouhu.spring.cloud.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudSentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudSentinelApplication.class, args);
    }

}
