package com.zou.springboot.canal.redis.canal;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Canal 监听 SQL 类型
 *
 * @author zouhu
 * @data 2024-10-31 13:24
 */
@Getter
@RequiredArgsConstructor
public enum  SqlType {
    INSERT("INSERT", "插入"),
    UPDATE("UPDATE", "更新"),
    DELETE("DELETE", "删除");

    private final String type;
    private final String name;
}
