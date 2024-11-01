package com.zou.springboot.canal.redis.canal.service;

import com.alibaba.otter.canal.protocol.FlatMessage;

import java.util.Collection;

/**
 *  Canal同步服务接口，用于处理来自Canal的数据同步请求
 *  该接口主要定义了如何处理数据变更事件，包括DDL语句执行和DML操作（插入、更新、删除）
 *
 * @author zouhu
 * @data 2024-10-31 15:16
 */
public interface CanalSyncService<T> {
    /**
     * 处理数据变更事件
     * <p>
     *     该方法用于处理来自Canal的数据变更事件，包括DDL语句执行和其他数据操作（如插入、更新和删除）
     * </p>
     *
     * @param flatMessage CanalMQ数据
     */
    void process(FlatMessage flatMessage);

    /**
     * DDL语句处理
     *
     * @param flatMessage CanalMQ数据
     */
    void ddl(FlatMessage flatMessage);

    /**
     * 插入
     *
     * @param list 新增数据
     */
    void insert(Collection<T> list);

    /**
     * 更新
     *
     * @param list 更新数据
     */
    void update(Collection<T> list);

    /**
     * 删除
     *
     * @param list 删除数据
     */
    void delete(Collection<T> list);
}
