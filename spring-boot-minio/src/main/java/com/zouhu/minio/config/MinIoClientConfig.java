package com.zouhu.minio.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 构建 Minio 客户端
 *
 * @author zouhu
 * @version 1.0
 * @data 2024-10-26 16:00
 */
@Configuration
public class MinIoClientConfig {
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return  MinioClient.builder()
                .endpoint(endpoint) // MinIO 服务器的 URL
                .credentials(accessKey, secretKey) // 访问密钥和密钥
                .build();
    }
}
