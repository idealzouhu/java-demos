[TOC]

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

```yaml
spring:
  application:
    name: elasticsearch-client

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
public class Account {
    // ES中 ducument 的 _id
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



### 2.4 连接 Elasticserach

```java
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

```



### 2.5 定义 Service 层接口

```java
public interface AccountElasticsearchService {

    /**
     * 将 Account 对象索引到 Elasticsearch 中。
     *
     * @param account 需要索引的账户对象
     * @throws IOException 当 Elasticsearch 请求失败时抛出
     */
    void indexAccount(Account account) throws IOException;

    /**
     * 批量将多个 Account 对象索引到 Elasticsearch 中。
     *
     * @param accounts 需要索引的账户对象列表
     * @throws IOException 当 Elasticsearch 请求失败时抛出
     */
    public void indexMultipleAccounts(List<Account> accounts) throws IOException;

    /**
     * 根据账户编号搜索账户信息。
     *
     * @param accountNumber 账户编号
     * @return 匹配的账户信息，以字符串形式返回
     * @throws IOException 当 Elasticsearch 请求失败时抛出
     */
    String searchAccountByAccountNumber(Long accountNumber) throws IOException;

    /**
     * 根据账户 ID 从 Elasticsearch 获取账户信息。
     *
     * @param id 账户 ID
     * @return 对应账户的详细信息
     * @throws IOException 当 Elasticsearch 请求失败时抛出
     */
    String  searchAccountById(String id) throws IOException;

    /**
     * 根据 firstname 和 age 查询账户。
     *
     * @param firstName 姓名
     * @param age 年龄
     * @throws IOException 当 Elasticsearch 请求失败时抛出
     */
    public void searchByNameAndAge(String firstName, int age) throws IOException;

    /**
     * 更新账户信息。
     *
     * @param account 修改后的账户对象
     * @return 返回更新结果信息
     * @throws IOException 当 Elasticsearch 请求失败时抛出
     */
    String updateAccountById(Account account) throws IOException;

    /**
     * 根据账户 ID 从 Elasticsearch 中删除账户信息。
     *
     * @param accountId 要删除的账户 ID
     * @return 返回删除结果信息
     * @throws IOException 当 Elasticsearch 请求失败时抛出
     */
     String deleteAccountById(String accountId) throws IOException;

}

```



### 2.6 实现 Service  层功能

```java
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountElasticsearchServiceImpl implements AccountElasticsearchService {

    private final ElasticsearchClient esClient;

    /**
     * 将 Account 对象索引到 Elasticsearch 中。
     *
     * @param account 需要索引的账户对象
     * @throws IOException 当 Elasticsearch 请求失败时抛出
     */
    @Override
    public void indexAccount(Account account) throws IOException {
        // 创建索引请求并执行
        IndexResponse createResponse = esClient.index(i -> i
                .index("accounts")  // 指定索引名称
                .id(account.getId())      // 文档 ID, 使用 account.getId() 获取文档的唯一标识
                .document(account)        // 文档内容
        );

        log.info("Document indexed with ID:{}", createResponse.id());
    }

    /**
     * 批量将多个 Account 对象索引到 Elasticsearch 中。
     *
     * @param accounts 需要索引的账户对象列表
     * @throws IOException 当 Elasticsearch 请求失败时抛出
     */
    @Override
    public void indexMultipleAccounts(List<Account> accounts) throws IOException {
        // 创建批量请求构建器
        BulkRequest.Builder bulkRequest = new BulkRequest.Builder();

        // 循环添加每个账户对象的索引请求
        for (Account account : accounts) {
            bulkRequest.operations(op -> op
                    .index(idx -> idx
                            .index("accounts")
                            .id(account.getId())
                            .document(account)
                    )
            );
        }

        // 执行批量请求
        BulkResponse bulkResponse = esClient.bulk(bulkRequest.build());

        // 打印出成功的文档 ID
        if (bulkResponse.errors()) {
            log.error("Bulk had errors");
            for (BulkResponseItem  item : bulkResponse.items()) {
                if (item.error() != null) {
                    log.error(item.error().reason());
                }
            }
        } else {
            log.info("Bulk indexing completed successfully");
        }
    }

   
    /**
     * 根据账户编号搜索账户信息。
     *
     * @param accountNumber 账户编号
     * @return 匹配的账户信息，以字符串形式返回
     * @throws IOException 当 Elasticsearch 请求失败时抛出
     */
    @Override
    public String searchAccountByAccountNumber(Long accountNumber) throws IOException {
        // 使用 Elasticsearch 客户端进行搜索
        SearchResponse<Account> searchResponse = esClient.search(s -> s
                        .index("accounts")  // 指定索引名称
                        .query(q -> q
                                .term(t -> t
                                        .field("account_number")  // 搜索字段
                                        .value(v -> v.longValue(accountNumber))  // 动态传入搜索值
                                )),
                Account.class);

        // 获取总命中数
        TotalHits totalHits = searchResponse.hits().total();
        boolean isExactResult = totalHits.relation() == TotalHitsRelation.Eq;
        if (isExactResult) {
            log.info("Found exactly " + totalHits.value() + " matching account(s).");
        } else {
            log.info("Found more than " + totalHits.value() + " matching account(s).");
        }

        // 构建返回结果
        StringBuilder resultBuilder = new StringBuilder("Search results:\n");
        for (Hit<Account> hit : searchResponse.hits().hits()) {
            Account foundAccount = hit.source();  // 获取匹配到的账户信息
            if (foundAccount != null) {
                resultBuilder.append(String.format(
                        "Account: %s, Name: %s %s\n",
                        foundAccount.getAccountNumber(),
                        foundAccount.getFirstname(),
                        foundAccount.getLastname()));
            }
        }

        // 返回结果
        if (resultBuilder.length() == 0) {
            return "No matching accounts found for account number: " + accountNumber;
        }
        return resultBuilder.toString();
    }

    /**
     * 根据账户 ID 从 Elasticsearch 获取账户信息。
     *
     * @param id 账户 ID, 也是文档的唯一标识
     * @return 对应账户的详细信息
     * @throws IOException 当 Elasticsearch 请求失败时抛出
     */
    @Override
    public String searchAccountById(String id) throws IOException {
        // 创建查询请求
        GetResponse<Account> response = esClient.get(g -> g
                        .index("accounts")
                        .id(id),
                Account.class
        );

        // 判断是否找到对应的账户
        if (response.found()) {
            // 获取账户信息
            Account account = response.source();
            // 将账户信息转换为 JSON 字符串并返回
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(account);
        }else {
            return "Account not found";
        }
    }

    /**
     * 根据 firstname 和 age 查询账户。
     *
     * @param firstName 姓名
     * @param age       年龄
     * @throws IOException 当 Elasticsearch 请求失败时抛出
     */
    @Override
    public void searchByNameAndAge(String firstName, int age) throws IOException {
        // 创建查询条件
        Query firstNameQuery = MatchQuery.of(m -> m.field("firstname").query(firstName))._toQuery();
        Query  ageQuery = MatchQuery.of(m -> m.field("age").query(age))._toQuery();

        // 使用 bool 查询将多个条件组合起来
        BoolQuery boolQuery = BoolQuery.of(
                b -> b.must(firstNameQuery).must(ageQuery)
        );

        // 创建搜索请求
        SearchRequest searchRequest = SearchRequest.of(builder -> builder
                .index("accounts")
                .query(q -> q.bool(boolQuery))
        );

        // 执行查询
        SearchResponse<Account> searchResponse = esClient.search(searchRequest, Account.class);

        // 打印结果
        System.out.println("Search results:");
        for (Hit<Account> hit : searchResponse.hits().hits()) {
            System.out.println("Found account: " + hit.source());
        }
    }

    /**
     * 根据账户 ID 更新账户信息。
     *
     * @param account 要更新的账户信息
     * @return 返回更新结果信息
     * @throws IOException 当 Elasticsearch 请求失败时抛出
     */
    @Override
    public String updateAccountById(Account account) throws IOException {
        // 创建更新请求
        UpdateRequest<Account, Account> updateRequest = UpdateRequest.of(builder -> builder
                .index("accounts")
                .id(account.getId())
                .doc(account)
        );

        // 执行更新操作
        UpdateResponse<Account> updateResponse = esClient.update(updateRequest, Account.class);

        // 根据更新结果构建响应信息
        return "Document updated with result: " + updateResponse.result();
    }

    /**
     * 根据账户 ID 从 Elasticsearch 中删除账户信息。
     *
     * @param accountId 要删除的账户 ID
     * @return 返回删除结果信息
     * @throws IOException 当 Elasticsearch 请求失败时抛出
     */
    @Override
    public String deleteAccountById(String accountId) throws IOException {
        // 创建删除请求
        DeleteRequest deleteRequest =  DeleteRequest.of(builder -> builder
                .index("accounts")  // 指定索引
                .id(accountId)            // 指定文档 ID
        );

        // 执行删除操作
        DeleteResponse deleteResponse = esClient.delete(deleteRequest);

        // 根据删除结果构建响应信息
        return "Document deleted with result: " + deleteResponse.result();
    }

}
```



## 三、测试项目

### 3.1 添加数据

具体测试代码为：

```java
@SpringBootTest
class AccountElasticsearchServiceImplTest {

    @Autowired
    private AccountElasticsearchService accountElasticsearchService;

    @Test
    void indexAccount() throws IOException {
        // 创建测试数据
        Account account = new Account();
        account.setId("12345");
        account.setAccountNumber(12345L);
        account.setEmail("john.doe@example.com");

        // 调用 indexAccount 方法
        accountElasticsearchService.indexAccount(account);
    }
}
```

运行结果为：

```sh
2024-12-07T13:10:42.684+08:00 TRACE 3592 --- [elasticsearch-client] [           main] tracer                                   : curl -iX PUT 'http://localhost:9200/accounts/_doc/12345' -d '{"id":"12345","email":"john.doe@example.com","account_number":12345}'
# HTTP/1.1 201 Created
# Location: /accounts/_doc/12345
# X-elastic-product: Elasticsearch
# content-type: application/json; charset=UTF-8
# content-length: 160
#
# {"_index":"accounts","_type":"_doc","_id":"12345","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":4,"_primary_term":1}
2024-12-07T13:10:42.714+08:00  INFO 3592 --- [elasticsearch-client] [           main] .e.c.s.i.AccountElasticsearchServiceImpl : Document indexed with ID:12345
```



### 3.2 搜索数据

具体测试代码为：

```java
@SpringBootTest
class AccountElasticsearchServiceImplTest {

    @Autowired
    private AccountElasticsearchService accountElasticsearchService;

  @Test
    void searchAccountByAccountNumber() throws IOException {
        String result = accountElasticsearchService.searchAccountByAccountNumber(12345L);
        System.out.println(result);
    }

}
```

运行结果为：

```shell
2024-12-07T13:11:14.508+08:00 TRACE 34672 --- [elasticsearch-client] [           main] tracer                                   : curl -iX POST 'http://localhost:9200/accounts/_search?typed_keys=true' -d '{"query":{"term":{"account_number":{"value":12345}}}}'
# HTTP/1.1 200 OK
# X-elastic-product: Elasticsearch
# content-type: application/json; charset=UTF-8
# content-length: 303
#
# {"took":711,"timed_out":false,"_shards":{"total":1,"successful":1,"skipped":0,"failed":0},"hits":{"total":{"value":1,"relation":"eq"},"max_score":1.0,"hits":[{"_index":"accounts","_type":"_doc","_id":"12345","_score":1.0,"_source":{"id":"12345","email":"john.doe@example.com","account_number":12345}}]}}
2024-12-07T13:11:14.718+08:00  INFO 34672 --- [elasticsearch-client] [           main] .e.c.s.i.AccountElasticsearchServiceImpl : Found exactly 1 matching account(s).
Search results:
Account: 12345, Name: null null
```





### 3.3 更新数据

具体测试代码为：

```java
@SpringBootTest
class AccountElasticsearchServiceImplTest {

    @Autowired
    private AccountElasticsearchService accountElasticsearchService;

  @Test
    void searchAccountByAccountNumber() throws IOException {
        String result = accountElasticsearchService.searchAccountByAccountNumber(12345L);
        System.out.println(result);
    }

}
```

运行结果为：

```shell
2024-12-07T13:12:56.867+08:00 TRACE 11832 --- [elasticsearch-client] [           main] tracer                                   : curl -iX POST 'http://localhost:9200/accounts/_update/12345' -d '{"doc":{"id":"12345","email":"john.doe@example.com","firstname":"John","lastname":"Doe","account_number":12345}}'
# HTTP/1.1 200 OK
# X-elastic-product: Elasticsearch
# content-type: application/json; charset=UTF-8
# content-length: 160
#
# {"_index":"accounts","_type":"_doc","_id":"12345","_version":2,"result":"updated","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":5,"_primary_term":1}
Document updated with result: Updated
```



### 3.4 删除数据

具体测试代码为：

```java
@SpringBootTest
class AccountElasticsearchServiceImplTest {

    @Autowired
    private AccountElasticsearchService accountElasticsearchService;

    @Test
    void deleteAccountById() {
        try {
            String result = accountElasticsearchService.deleteAccountById("12345L");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
```

运行结果为：

```shell
2024-12-07T13:15:10.064+08:00 TRACE 13804 --- [elasticsearch-client] [           main] tracer                                   : curl -iX DELETE 'http://localhost:9200/accounts/_doc/12345'
# HTTP/1.1 200 OK
# X-elastic-product: Elasticsearch
# content-type: application/json; charset=UTF-8
# content-length: 160
#
# {"_index":"accounts","_type":"_doc","_id":"12345","_version":3,"result":"deleted","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":6,"_primary_term":1}
Document deleted with result: Deleted
```





## 参考资料

[Connecting | Elasticsearch Java API Client 7.17](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.17/connecting.html)

[ElasticSearch8 - SpringBoot整合ElasticSearch - 王谷雨 - 博客园](https://www.cnblogs.com/konghuanxi/p/18094055)