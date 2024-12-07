package com.zouhu.elasticsearch.starter.service;

import com.zouhu.elasticsearch.starter.entity.Account;

import java.util.List;

public interface AccountService {
    Account saveAccount(Account account);

    Account getAccountById(String id);

    List<Account> getAccountsByFirstname(String firstname);

    List<Account> getAccountsByAgeRange(int minAge, int maxAge);

    void deleteAccount(String id);
}
