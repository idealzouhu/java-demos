package com.zouhu.elasticsearch.client.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zouhu
 * @data 2024-12-05 23:04
 */
@Configuration
public class ElasticsearchConfig {

    /**
     * 创建并返回一个 Elasticsearch 客户端。
     *
     * <p>
     *     使用 RestClientBuilder 构建 RestClient，并使用 BasicCredentialsProvider 设置用户名和密码。
     *     <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.17/connecting.html">官方客户端连接教程</a>
     *     <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.17/_basic_authentication.html">官方基本认证教程</a>
     * </p>
     *
     * @return 初始化好的 Elasticsearch 客户端
     */
    @Bean
    public ElasticsearchClient esClient() {
        // 1. 配置认证
        String userName = "elastic";
        String password = "Elastic@123456";
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY, new UsernamePasswordCredentials(userName, password)
        );

        // 2. 创建 low-level 客户端（使用身份验证）
        RestClient restClient = RestClient.builder(
                        new HttpHost("localhost", 9200))    // 指定 Elasticsearch 服务器的主机名和端口号
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                )
                .build();

        // 3. 创建传输对象，使用 Jackson 映射器将 Java 对象转换为 JSON 格式
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // 4. 创建 API 客户端
        return new ElasticsearchClient(transport);
    }

}
