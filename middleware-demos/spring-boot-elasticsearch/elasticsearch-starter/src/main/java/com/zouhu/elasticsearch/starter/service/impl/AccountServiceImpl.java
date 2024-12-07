package com.zouhu.elasticsearch.starter.service.impl;

import com.zouhu.elasticsearch.starter.entity.Account;
import com.zouhu.elasticsearch.starter.repository.AccountRepository;
import com.zouhu.elasticsearch.starter.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zouhu
 * @data 2024-12-07 17:05
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl  implements AccountService {

    private  final AccountRepository accountRepository;

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountById(String id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<Account> getAccountsByFirstname(String firstname) {
        return accountRepository.findByFirstname(firstname);
    }

    @Override
    public List<Account> getAccountsByAgeRange(int minAge, int maxAge) {
        return accountRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }
}
