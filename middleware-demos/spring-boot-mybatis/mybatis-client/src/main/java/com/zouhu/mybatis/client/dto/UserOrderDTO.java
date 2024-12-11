package com.zouhu.mybatis.client.dto;

import lombok.Data;

/**
 *  创建一个新的数据传输对象（DTO）类，用于存储查询结果。
 *  这个类可以包含用户和订单的相关字段，而不需要更改原有的 User 和 Orders 实体类。
 *
 * @author zouhu
 * @data 2024-08-14 16:55
 */
@Data
public class UserOrderDTO {
    private Long userId;
    private String userName;
    private String orderNumber;
}
