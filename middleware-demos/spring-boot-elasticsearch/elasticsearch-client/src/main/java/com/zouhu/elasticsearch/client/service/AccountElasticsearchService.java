package com.zouhu.elasticsearch.client.service;

import com.zouhu.elasticsearch.client.entity.Account;

import java.io.IOException;
import java.util.List;

/**
 * AccountElasticsearchService 接口 ， 定义了与 Elasticsearch 中 Account 相关的业务操作
 * <p>
 *     <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.17/aggregations.html">Java API Client 官方教程</a>
 * </p>
 *
 * @author zouhu
 * @data 2024-12-06 17:10
 */
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
     * 从指定的 JSON 文件批量导入 Account 数据到 Elasticsearch。
     *
     * <p>
     *     使用 BinaryData 直接传递 JSON 文本内容，避免将 JSON 解析为 Java 对象再序列化成 JSON，从而节省资源和提升性能
     * </p>
     *
     * @param filePath JSON 文件的路径
     * @return 导入结果信息
     * @throws IOException 如果文件读取或 Elasticsearch 操作失败
     */
    String bulkImportAccountsFromJson(String filePath) throws IOException;

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
