package com.zouhu.springboot.filter.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class LoggingFilterTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试 `LoggingFilter` 在访问受保护的 `/secure/*` 路径时的行为。
     */
    @Test
    void testLoggingFilterOnSecureEndpoint() throws Exception {
        mockMvc.perform(get("/secure/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello, this is a secure endpoint!"));
    }

    /**
     * 测试当访问不受过滤器保护的公共路径 `/public/*` 时，过滤器不生效。
     */
    @Test
    void testLoggingFilterOnPublicEndpoint() throws Exception {
        mockMvc.perform(get("/public/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("This is a public endpoint, no filter applied!"));
    }

}