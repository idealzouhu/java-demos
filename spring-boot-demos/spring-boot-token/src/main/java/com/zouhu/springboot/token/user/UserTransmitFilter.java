package com.zouhu.springboot.token.user;

import com.zouhu.springboot.token.dto.UserInfoDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLDecoder;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 用户传输过滤器
 * <p>
 *     如果 HTTP 请求 Header 中包含用户信息，则进行解析并放入 UserContext
 * </p>
 *
 * @author zouhu
 * @data 2024-11-07 18:25
 */
public class UserTransmitFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        System.out.println("UserTransmitFilter doFilter");

        // 从请求中提取用户信息，将其封装到 UserInfoDTO 对象中，并设置到用户上下文中
        String userId = httpServletRequest.getHeader(UserConstant.USER_ID_KEY);
        if (StringUtils.hasText(userId)) {
            String userName = httpServletRequest.getHeader(UserConstant.USER_NAME_KEY);
            String realName = httpServletRequest.getHeader(UserConstant.REAL_NAME_KEY);
            if (StringUtils.hasText(userName)) {
                userName = URLDecoder.decode(userName, UTF_8);
            }
            if (StringUtils.hasText(realName)) {
                realName = URLDecoder.decode(realName, UTF_8);
            }
            String token = httpServletRequest.getHeader(UserConstant.USER_TOKEN_KEY);
            UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                    .userId(userId)
                    .username(userName)
                    .realName(realName)
                    .token(token)
                    .build();
            UserContext.setUser(userInfoDTO);
        }

        // 在请求处理完成后，通过finally块确保用户信息被正确移除，以避免内存泄漏
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }
}

