package com.zouhu.aop.log;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest // 启动整个应用上下文
@AutoConfigureMockMvc // 自动配置 MockMvc
class ILogPrintAspectTest {
    @Autowired
    private MockMvc mockMvc; // MockMvc用于测试控制器

    @Autowired
    private TestService testService;
    @Test
    public void testPerformAction() throws Exception {
        log.info("This is an info message");

        // 测试 ILogPrintAspect
        System.out.println("Spring AOP 示例1：");
        System.out.println("======================================================================================");
        testService.doSomething();
        System.out.println("======================================================================================\n");

        // 模拟 GET 请求到 "/api/test" 端点
        System.out.println("Spring AOP 示例1：");
        System.out.println("======================================================================================");
        mockMvc.perform(get("/api/test")
                        .param("input", "Test input")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 验证响应状态码
                .andExpect(content().string("Processed: Test input")); // 验证响应内容
        System.out.println("======================================================================================\n");

    }
}