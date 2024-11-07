package com.zouhu.springboot.security.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class WebSecurityConfigTest {

    @Autowired
    private MockMvc mvc;

    /**
     *  测试未认证用户访问主页和受限资源
     */
    // @WithMockUser  // 未加 @WithMockUser 表示请求由未认证用户发出
    @Test
    void Unauthenticated() throws Exception {
        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to the Home Page!")); // 预期返回 200 OK

        this.mvc.perform(get("/admin/secure"))
                .andExpect(status().isUnauthorized())  // 预期返回 401 Unauthorized
                .andExpect(header().string("WWW-Authenticate", "Basic realm=\"Realm\""));
    }


    /**
     * 测试默认用户（无特殊权限）的访问
     */
    @WithMockUser  // 模拟一个没有设置特定权限的默认用户
    @Test
    void NotAuthorityUser() throws Exception {
        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to the Home Page!")); // 预期返回 200 OK

        this.mvc.perform(get("/user/secure"))
                .andExpect(status().isForbidden()); // 预期返回 403 Forbidden
    }

    /**
     *  测试具有 USER 权限的用户访问受限资源
     */
    @WithMockUser(authorities = "USER")
    @Test
    void UserAuthority() throws Exception {
        this.mvc.perform(get("/user/secure"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello User!"));  // 预期返回 200 OK

        this.mvc.perform(get("/admin/secure"))
                .andExpect(status().isForbidden());  // 预期返回  403 Forbidden
    }

    /**
     *  测试具有 ADMIN 权限的用户访问受限资源
     */
    @WithMockUser(authorities = "ADMIN")
    @Test
    void AdminAuthority() throws Exception {
        this.mvc.perform(get("/user/secure"))
                .andExpect(status().isForbidden()); // 预期返回 403 Forbidden

        this.mvc.perform(get("/admin/secure"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Admin!"));  // 预期返回 200 OK
    }
}