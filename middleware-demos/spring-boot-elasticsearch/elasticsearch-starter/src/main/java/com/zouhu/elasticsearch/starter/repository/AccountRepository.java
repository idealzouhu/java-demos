package com.zouhu.elasticsearch.starter.repository;

import com.zouhu.elasticsearch.starter.entity.Account;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AccountRepository  extends ElasticsearchRepository<Account, String> {
    List<Account> findByFirstname(String firstname);
    List<Account> findByAgeBetween(int minAge, int maxAge);
}
