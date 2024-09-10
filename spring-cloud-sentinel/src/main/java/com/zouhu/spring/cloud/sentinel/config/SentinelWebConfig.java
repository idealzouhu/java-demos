package com.zouhu.spring.cloud.sentinel.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.PrintWriter;

/**
 * 处理限流或者降级时的异常情况
 *
 * @author zouhu
 * @data 2024-09-09 22:10
 */
@Configuration
public class SentinelWebConfig {
    @Bean
    public BlockExceptionHandler sentinelBlockExceptionHandler() {
        return (request, response, e) -> {
            // 429 Too Many Requests
            response.setStatus(429);

            PrintWriter out = response.getWriter();
            out.print("Oops, blocked by Sentinel: " + e.getClass().getSimpleName());
            out.flush();
            out.close();
        };
    }
}
