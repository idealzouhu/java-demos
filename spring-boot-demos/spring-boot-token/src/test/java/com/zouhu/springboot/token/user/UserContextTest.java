package com.zouhu.springboot.token.user;

import com.zouhu.springboot.token.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class UserContextTest {
    @Autowired
    private MockMvc mockMvc;  // 用于模拟请求

    @Autowired
    private WebApplicationContext context;

    /**
     * 测试 UserContext 的设置和获取功能
     * 注意：使用的时候，注释过滤器中的 ’UserContext.removeUser();‘ 语句
     *
     * @throws Exception
     */
    @Test
    public void testSetUserContext() throws Exception {
        // 将过滤器应用到 MockMvc 中
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new UserTransmitFilter())
                .build();

        // 准备测试数据
        String userId = "123";
        String username = "testUser";
        String realName = "Test User";

        // 模拟登录，假设服务器生成了一个 token
        String token = "Bearer sampleToken";

        // 模拟请求头传递用户信息并发送 HTTP 请求
        mockMvc.perform(get("/hello")  // 替换为你实际的请求路径
                        .header(UserConstant.USER_ID_KEY, userId)
                        .header(UserConstant.USER_NAME_KEY, username)
                        .header(UserConstant.REAL_NAME_KEY, realName)
                        .header(UserConstant.USER_TOKEN_KEY, token))
                .andExpect(status().isOk())  // 验证 HTTP 状态码
                .andExpect(result -> {
                    // 校验 UserContext 中存储的用户信息
                    assertEquals(userId, UserContext.getUserId());
                    assertEquals(username, UserContext.getUsername());
                    assertEquals(realName, UserContext.getRealName());
                    assertEquals(token, UserContext.getToken());
                });
    }
}