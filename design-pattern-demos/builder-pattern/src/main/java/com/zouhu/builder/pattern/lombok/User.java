package com.zouhu.builder.pattern.lombok;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 使用 @Builder 注解创建 Builder 类
 *
 * @author zouhu
 * @data 2024-09-04 17:41
 */
@Data
@Builder
public class User {
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

}
