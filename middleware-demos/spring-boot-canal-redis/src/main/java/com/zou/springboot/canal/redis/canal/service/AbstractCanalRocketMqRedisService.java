package com.zou.springboot.canal.redis.canal.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.otter.canal.protocol.FlatMessage;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.common.collect.Sets;
import com.zou.springboot.canal.redis.canal.SqlType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 抽象Canal-RocketMQ通用处理服务
 *
 *
 * @author zouhu
 * @data 2024-10-31 15:21
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractCanalRocketMqRedisService<T> implements CanalSyncService<T> {

    private final RedisTemplate<String, Object> redisTemplate;

    private Class<T> classCache;


    /**
     * 获取Model名称
     *
     * @return Model名称
     */
    protected abstract String getModelName();

    /**
     * 处理数据
     * <p>
     *     后续优化：可以使用策略模式来封装不同表的操作，不一定要统一
     * </p>
     *
     * @param flatMessage CanalMQ数据
     */
    @Override
    public void process(FlatMessage flatMessage) {

        if (flatMessage.getIsDdl()) {
            ddl(flatMessage);
            return;
        }

        Set<T> data = getData(flatMessage);

        if (SqlType.INSERT.getType().equals(flatMessage.getType())) {
            insert(data);
        }

        if (SqlType.UPDATE.getType().equals(flatMessage.getType())) {
            update(data);
        }

        if (SqlType.DELETE.getType().equals(flatMessage.getType())) {
            delete(data);
        }

    }

    /**
     * DDL语句处理
     *
     * @param flatMessage CanalMQ数据
     */
    @Override
    public void ddl(FlatMessage flatMessage) {
        //TODO : DDL需要同步，删库清空，更新字段处理
    }

    /**
     * 插入
     *
     * @param list 新增数据
     */
    @Override
    public void insert(Collection<T> list) {
        insertOrUpdate(list);
    }

    /**
     * 更新
     *
     * @param list 更新数据
     */
    @Override
    public void update(Collection<T> list) {
        insertOrUpdate(list);
    }

    /**
     * 删除
     *
     * @param list 删除数据
     */
    @Override
    public void delete(Collection<T> list) {
        Set<String> keys = Sets.newHashSetWithExpectedSize(list.size());

        for (T data : list) {
            keys.add(getWrapRedisKey(data));
        }

        redisTemplate.delete(keys);
    }

    /**
     * 插入或者更新redis
     * <p>
     *     data 对象里面还包含 getTypeArgument()的返回值，但是没有写到 Redis 里面
     * </p>
     *
     * @param list 数据
     */
    private void insertOrUpdate(Collection<T> list) {
        for (T data : list) {
            log.info("redis data:{}", data);
            String key = getWrapRedisKey(data);
            log.info("redis key:{}", key);
            redisTemplate.opsForValue().set(key, data);
        }
    }

    /**
     * 封装redis的key
     *
     * @param t 原对象
     * @return key
     */
    protected String getWrapRedisKey(T t) {
        return getModelName() + ":" + getIdValue(t);
    }

    /**
     * 获取类泛型
     *
     * @return 泛型Class
     */
    @SuppressWarnings("unchecked")
    protected Class<T> getTypeArgument() {
        if (classCache == null) {
            classCache = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return classCache;
    }

    /**
     * 获取 Object 标有 @TableId 注解的字段值
     *
     * @param t 对象
     * @return id值
     */
    protected Object getIdValue(T t) {
        Field fieldOfId = getIdField();
        ReflectionUtils.makeAccessible(fieldOfId);
        return ReflectionUtils.getField(fieldOfId, t);
    }

    /**
     * 获取Class标有@TableId注解的字段名称
     *
     * @return id字段名称
     */
    protected Field getIdField() {
        Class<T> clz = getTypeArgument();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            TableId annotation = field.getAnnotation(TableId.class);

            if (annotation != null) {
                return field;
            }
        }
        log.error("PO类未设置@TableId注解");
        throw new RuntimeException("PO类未设置@TableId注解");
    }

    /**
     * 转换 Canal 的 FlatMessage中的data成泛型对象
     *
     * @param flatMessage Canal发送MQ信息
     * @return 泛型对象集合
     */
    protected Set<T> getData(FlatMessage flatMessage) {
        List<Map<String, String>> sourceData = flatMessage.getData();
        Set<T> targetData = Sets.newHashSetWithExpectedSize(sourceData.size());
        for (Map<String, String> map : sourceData) {
            // 将Type类型的数据和T对象合并转换为泛型对象T
            T t = JSON.parseObject(JSON.toJSONString(map), getTypeArgument());
            targetData.add(t);
        }
        return targetData;
    }

}
