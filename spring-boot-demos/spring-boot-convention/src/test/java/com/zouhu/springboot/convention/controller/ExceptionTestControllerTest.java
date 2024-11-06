package com.zouhu.springboot.convention.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * 测试全局异常处理
 */
@SpringBootTest
@AutoConfigureMockMvc
class ExceptionTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void throwClientException() throws Exception {
        MvcResult result = mockMvc.perform(get("/test/client"))
                .andReturn();

        // 打印响应内容
        result.getResponse().setCharacterEncoding("UTF-8");
        System.out.println("Response Content: " + result.getResponse().getContentAsString());
    }

    @Test
    void throwServerException() throws Exception {
        mockMvc.perform(get("/test/server"));
    }

    @Test
    void throwThirdPartyException() throws Exception {
        mockMvc.perform(get("/test/thirdparty"));
    }

    @Test
    void throwUnknownException() throws Exception {
        mockMvc.perform(get("/test/unknown"));
    }
}