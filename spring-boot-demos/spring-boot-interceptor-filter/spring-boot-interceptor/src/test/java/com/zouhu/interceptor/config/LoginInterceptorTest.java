package com.zouhu.interceptor.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

/**
 * 测试登录拦截器
 */
@SpringBootTest
@AutoConfigureMockMvc
class LoginInterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    private MockHttpSession session;

    @BeforeEach
    public void setup() {
        session = new MockHttpSession();
    }

    /**
     * 模拟未登录用户访问受保护的资源，应该重定向到登录页面
     */
    @Test
    void shouldRedirectToLoginIfNotLoggedIn() throws Exception {
        mockMvc.perform(get("/secure/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    /**
     * 模拟已登录用户访问受保护的资源，应该允许访问
     */
    @Test
    void shouldAllowAccessIfLoggedIn() throws Exception {
        // 模拟已登录用户
        session.setAttribute("user", "testUser");

        // 发送请求并验证结果
        mockMvc.perform(get("/secure/home").session(session))
                .andExpect(status().isOk()); // 期待返回 200 状态码
    }
}