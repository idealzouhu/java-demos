package com.zouhu.elasticsearch.client.service.impl;

import com.zouhu.elasticsearch.client.entity.Account;
import com.zouhu.elasticsearch.client.service.AccountElasticsearchService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountElasticsearchServiceImplTest {

    @Autowired
    private AccountElasticsearchService accountElasticsearchService;

    @Test
    void indexAccount() throws IOException {
        // 创建测试数据
        Account account = new Account();
        account.setId("12375");
        account.setAccountNumber(12375L);
        account.setEmail("jaohn.doe@example.com");

        // 调用 indexAccount 方法
        accountElasticsearchService.indexAccount(account);
    }

    @Test
    void indexMultipleAccounts() throws IOException {
        // 创建测试数据
        Account account1 = new Account();
        account1.setId("19568");
        account1.setAccountNumber(12345L);
        account1.setFirstname("John");
        account1.setAge(30);
        account1.setEmail("john.doe@example.com");

        Account account2 = new Account();
        account2.setId("67590");
        account2.setAccountNumber(67890L);
        account2.setEmail("jane.doe@example.com");

        // 调用indexMultipleAccounts 方法
        accountElasticsearchService.indexMultipleAccounts(java.util.Arrays.asList(account1, account2));
    }

    @Test
    void bulkImportAccountsFromJson() throws IOException {
        String result = accountElasticsearchService.bulkImportAccountsFromJson("src/test/resources/accounts.json");
        System.out.println(result);
    }

    @Test
    void searchAccountByAccountNumber() throws IOException {
        String result = accountElasticsearchService.searchAccountByAccountNumber(12345L);
        System.out.println(result);
    }

    @Test
    void searchAccountById() {
        try {
            String result = accountElasticsearchService.searchAccountById("12345");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void searchByNameAndAge() throws IOException {
        accountElasticsearchService.searchByNameAndAge("John", 30);
    }

    @Test
    void updateAccountById() throws IOException {
        // 创建模拟账户对象
        Account account = new Account();
        account.setId("12345");
        account.setAccountNumber(12345L);
        account.setEmail("john.doe@example.com");
        account.setFirstname("John");
        account.setLastname("Doe");

        // 调用 updateAccountById 方法
        String result = accountElasticsearchService.updateAccountById(account);
        System.out.println(result);
    }

    @Test
    void deleteAccountById() {
        try {
            String result = accountElasticsearchService.deleteAccountById("12345");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}