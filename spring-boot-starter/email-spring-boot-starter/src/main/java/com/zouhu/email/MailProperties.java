package com.zouhu.email;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 邮件配置属性类
 * <p>
 *     这个将绑定邮件服务器的配置信息。
 *     配置信息在配置文件 application.yaml 中读取
 * </p>
 *
 * @author zouhu
 * @data 2024-09-05 20:59
 */
@Data
@ConfigurationProperties(prefix = "mail")  // 自动获取配置文件application.yaml中前缀为mail的配置
public class MailProperties {
    // 如果配置文件中配置了host属性，则该默认属性会被覆盖
    private String host = "xxx";
    private int port = 465;
    private String username = "xxx";
    private String password = "xxx";
    private String from = "xxx";

}
