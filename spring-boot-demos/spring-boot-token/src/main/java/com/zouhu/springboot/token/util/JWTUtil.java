package com.zouhu.springboot.token.util;

import com.zouhu.springboot.token.dto.UserInfoDTO;
import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.zouhu.springboot.token.user.UserConstant.*;

/**
 *
 *
 * @author zouhu
 * @data 2024-11-07 17:42
 */
@Slf4j
public final class JWTUtil {
    // 令牌有效期 24 小时
    private static final long EXPIRATION = 86400L;

    // 生成 Token 前缀
    public static final String TOKEN_PREFIX = "Bearer ";

    // 签发者
    public static final String ISS = "zouhu";

    // 密钥, 用于生成 Token 的数字签名部分
    public static final String SECRET = "SecretKey039245678901232039487623456783092349288901402967890140939827";

    /**
     * 生成用户 Token
     * <p>
     *     数字签名组成部分：
     *     1.头部：签名算法。
     *     2.负载：用户的信息。
     *     3.签名：通过指定的算法，结合头部和负载，使用密钥对 JWT 进行签名
     *
     * </p>
     *
     * @param userInfo 用户信息
     * @return 用户访问 Token
     */
    public static String generateAccessToken(UserInfoDTO userInfo) {
        Map<String, Object> customerUserMap = new HashMap<>();
        customerUserMap.put(USER_ID_KEY, userInfo.getUserId());
        customerUserMap.put(USER_NAME_KEY, userInfo.getUsername());
        customerUserMap.put(REAL_NAME_KEY, userInfo.getRealName());
        String jwtToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)  // 设置签名算法和密钥，属于头部
                .setIssuedAt(new Date())  // 设置签发时间，属于负载
                .setIssuer(ISS)  // 设置签发者，属于负载
                .setSubject(JSON.toJSONString(customerUserMap))  // 设置主题，属于负载
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))  // 设置过期时间，属于负载
                .compact();  // 生成最终的 JWT Token

        return TOKEN_PREFIX + jwtToken;
    }

    /**
     * 解析用户 Token
     *
     * @param jwtToken 用户访问 Token
     * @return 用户信息
     */
    public static UserInfoDTO parseJwtToken(String jwtToken) {
        if (StringUtils.hasText(jwtToken)) {
            String actualJwtToken = jwtToken.replace(TOKEN_PREFIX, "");
            try {
                Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(actualJwtToken).getBody();
                Date expiration = claims.getExpiration();
                if (expiration.after(new Date())) {
                    String subject = claims.getSubject();
                    return JSON.parseObject(subject, UserInfoDTO.class);
                }
            } catch (ExpiredJwtException ignored) {
            } catch (Exception ex) {
                log.error("JWT Token解析失败，请检查", ex);
            }
        }
        return null;
    }
}
