package com.zouhu.elasticsearch.client;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;

/**
 * 简单案例
 *
 * @author zouhu
 * @data 2024-12-05 23:01
 */
public class ElasticsearchExample {

    // 定义一个Product类，代表文档的数据模型
    @Data
    @AllArgsConstructor
    public static class Product {
        private String name;
        private double price;
    }

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
    public static ElasticsearchClient createElasticsearchClient() {
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


    public static void main(String[] args) throws IOException {
        // 调用函数创建 Elasticsearch 客户端
        ElasticsearchClient esClient = createElasticsearchClient();

        // 创建 document
        Product product = new Product("bicycle", 100.50);
        product.setName("bicycle");
        product.setPrice(100.50);
        IndexResponse createResponse = esClient.index(i -> i
                .index("products")  // 指定索引名称
                .id("1")            // 文档 ID
                .document(product)        // 文档内容
        );
        System.out.println("文档已创建，ID: " + createResponse.id());

        // 搜索 document
        SearchResponse<Product> searchResponse = esClient.search(s -> s
                        .index("products")  // 指定索引
                        .query(q -> q
                                .term(t -> t
                                        .field("name")  // 搜索字段
                                        .value(v -> v.stringValue("bicycle"))  // 搜索值
                                )),
                Product.class);
        for (Hit<Product> hit : searchResponse.hits().hits()) {
            Product foundProduct  = hit.source();
            assert foundProduct != null;
            System.out.printf("Found product: %s with price: %.2f%n",
                    foundProduct.getName(), foundProduct.getPrice());  // 处理文档中的数据
        }

        // 删除 document
        DeleteResponse deleteResponse = esClient.delete(d -> d
                .index("products")  // 指定索引名称
                .id("1")            // 文档 ID
        );
        System.out.println("文档删除结果: " + deleteResponse.result());

        // 关闭客户端
        esClient.close();

    }
}
