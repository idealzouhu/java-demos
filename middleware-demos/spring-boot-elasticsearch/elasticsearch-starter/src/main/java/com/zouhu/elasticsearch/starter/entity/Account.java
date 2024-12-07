package com.zouhu.elasticsearch.starter.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 用户实体类
 *
 * @author zouhu
 * @data 2024-12-06 14:23
 */
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
