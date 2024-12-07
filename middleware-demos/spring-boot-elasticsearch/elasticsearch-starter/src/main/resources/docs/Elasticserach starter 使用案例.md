## 一、安装 Elasticsearch

### 1.1 启动 Elasticsearch

运行以下命令启动 Elasticsearch ：

```shell
$ docker network create elastic

$ docker pull docker.elastic.co/elasticsearch/elasticsearch:7.17.26

$ docker run --name es01-test ^
  --net elastic ^
  -p 127.0.0.1:9200:9200 ^
  -p 127.0.0.1:9300:9300 ^
  -e "discovery.type=single-node" ^
  -e "xpack.security.enabled=true" ^
  -e "ELASTIC_PASSWORD=Elastic@123456" ^
  -e "KIBANA_PASSWORD=Kibana@123456" ^
  docker.elastic.co/elasticsearch/elasticsearch:7.17.26
```

其中，具体参数的含义如下：

- `-e "xpack.security.enabled=true"`：启用 Elasticsearch 的安全功能。具体细节查看 [Set up minimal security for Elasticsearch ](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/security-minimal-setup.html)
- `-e "ELASTIC_PASSWORD=Elastic@123456"`：为 `elastic` 用户设置密码。 `elastic` 用户拥有超级权限。
- `-e "KIBANA_PASSWORD=Kibana@123456"`：为 `kibana_system` 用户设置密码。`kibana_system` 用户是一个为 **Kibana 服务账户** 创建的专用用户。它有足够的权限来与 Elasticsearch 通信，但它并没有 `elastic` 用户的超高权限。



### 1.2 启动 Kibana

Kibana 主要用来可视化和管理Elasticsearch数据。

运行以下命令启动 **Kibana**：

```shell
$ docker pull docker.elastic.co/kibana/kibana:7.17.26

$ docker run --name kib01-test ^
  --net elastic ^
  -p 127.0.0.1:5601:5601 ^
  -e "ELASTICSEARCH_HOSTS=http://es01-test:9200" ^
  -e "ELASTICSEARCH_USERNAME=elastic" ^
  -e "ELASTICSEARCH_PASSWORD=Elastic@123456" ^
  docker.elastic.co/kibana/kibana:7.17.26
```

其中，具体参数的含义如下：

- `ELASTICSEARCH_HOSTS=http://es01-test:9200`： 指定了 Kibana 应该连接到哪个 Elasticsearch 节点。

- `ELASTICSEARCH_USERNAME=elastic`： 指定 Kibana 使用的用户名是 `elastic`。
- `ELASTICSEARCH_PASSWORD=Elastic@123456`： `elastic` 用户的密码。

> 注意，我们也可以直接在 HTTP 请求里面设置用户名和参数，例如 `curl -u elastic:your_elastic_password http://localhost:9200`

在启动 Kibana 后，访问 Kibana 可视化界面 [http://localhost:5601](http://localhost:5601/)。





## 二、客户端代码

### 2.1 导入依赖

创建 Spring Boot 3.0.6 项目，导入相关[依赖](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.17/installation.html)：

```xml
<dependency>
    <groupId>co.elastic.clients</groupId>
    <artifactId>elasticsearch-java</artifactId>
    <version>7.17.26</version>
    <exclusions>
        <exclusion>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-client</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>elasticsearch-rest-client</artifactId>
    <version>7.17.26</version>
</dependency>

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.17.0</version>
</dependency>
```

如果遇到相关依赖冲突，可以查看 [Installation | Elasticsearch Java API Client 7.17](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.17/installation.html)





### 2.2 配置 application.yaml

配置认证信息。

```yaml
spring:
  application:
    name: elasticsearch-starter

  # es 配置
  elasticsearch:
    uris: http://localhost:9200   # Elasticsearch 服务地址
    username: elastic             # 用户名（如果有权限）
    password: Elastic@123456      # 密码（如果有权限）

# 打印 es 的 http 请求，适合在开发调试中使用，正式环境请关闭
logging:
  level:
    tracer: TRACE
```



### 2.3 定义实体类

```java
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Document(indexName = "accounts")
public class Account {
    @Id // ES中 ducument 的 _id
    private String id;

    // 解决ES中字段与实体类字段不一致的问题
    @JsonProperty("account_number")

    private Long accountNumber;
    private String address;
    private Integer age;
    private Long balance;
    private String city;
    private String email;
    private String employer;
    private String firstname;
    private String lastname;
    private String gender;
    private String state;
}
```



### 2.3 定义 Service 层接口

```java

```



### 2.4 实现 Service  层功能

```java

```



## 三、测试项目

### 3.1 添加数据

具体测试代码为：

```java

```

运行结果为：

```sh

```



### 3.2 搜索数据

具体测试代码为：

```java

```

运行结果为：

```shell

```





### 3.3 更新数据

具体测试代码为：

```java

```

运行结果为：

```shell

```



### 3.4 删除数据

具体测试代码为：

```java

```

运行结果为：

```shell

```





## 参考资料

[Spring Data Elasticsearch](https://spring.io/projects/spring-data-elasticsearch)

[ElasticSearch8 - SpringBoot整合ElasticSearch - 王谷雨 - 博客园](https://www.cnblogs.com/konghuanxi/p/18094055)