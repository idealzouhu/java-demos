package com.zouhu.elasticsearch.client.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import co.elastic.clients.util.BinaryData;

import co.elastic.clients.util.ContentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zouhu.elasticsearch.client.entity.Account;
import com.zouhu.elasticsearch.client.service.AccountElasticsearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 *  AccountElasticsearchService 的实现类，封装了对 Elasticsearch 的具体操作逻辑。
 *
 *  <p>
 *      注意，根据 document id 和 普通字段查询的API不太一样
 *  </p>
 *
 * @author zouhu
 * @data 2024-12-06 17:12
 */
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
     * 从指定的 JSON 文件批量导入 Account 数据到 Elasticsearch。
     *
     * <p>
     *     代优化事项：使用 BinaryData 直接传递 JSON 文本内容，避免将 JSON 解析为 Java 对象再序列化成 JSON，从而节省资源和提升性能
     * </p>
     *
     * @param filePath JSON 文件的路径
     * @return 导入结果信息
     * @throws IOException 如果文件读取或 Elasticsearch 操作失败
     */
    @Override
    public String bulkImportAccountsFromJson(String filePath) throws IOException {
        // 验证文件路径
        File jsonFile = new File(filePath);
        if (!jsonFile.exists() || !jsonFile.isFile()) {
            return "Invalid file path: " + filePath;
        }

        // 创建 BulkRequest 对象
        BulkRequest.Builder bulkRequest = new BulkRequest.Builder();

        FileInputStream input = new FileInputStream(filePath);
        BinaryData data = BinaryData.of(IOUtils.toByteArray(input), ContentType.APPLICATION_JSON);

        //TODO(zouhu, 2024-12-31): 优化请求，目前只能处理包含单个 JSON 对象的 JSON 文件
        bulkRequest.operations(op -> op
                .index(idx -> idx
                        .index("logs")
                        .document(data)
                )
        );

        BulkResponse bulkResponse = esClient.bulk(bulkRequest.build());

        return "Bulk import completed with " + bulkResponse.items().stream()
                .map(BulkResponseItem::result).filter(Objects::nonNull)
                .filter(result -> !result.equals("created"))
                .count() + " errors.";
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
