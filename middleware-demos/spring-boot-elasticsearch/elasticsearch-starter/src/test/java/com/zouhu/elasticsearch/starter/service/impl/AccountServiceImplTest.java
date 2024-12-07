package com.zouhu.elasticsearch.starter.service.impl;

import com.zouhu.elasticsearch.starter.entity.Account;
import com.zouhu.elasticsearch.starter.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceImplTest {
    @Autowired
    private AccountService accountService;


    @Test
    void saveAccount() {
        Account account = new Account();
        account.setId("1");
        account.setFirstname("Alice");
        account.setLastname("Smith");
        account.setAge(28);

        accountService.saveAccount(account);
    }

    @Test
    void getAccountById() {
        Account fetchedAccount = accountService.getAccountById("1");
        System.out.println(fetchedAccount);
    }

    @Test
    void getAccountsByFirstname() {
        accountService.getAccountsByFirstname("Alice");
    }

    @Test
    void getAccountsByAgeRange() {
        accountService.getAccountsByAgeRange(27, 29);
    }

    @Test
    void deleteAccount() {
        accountService.deleteAccount("1");
    }
}