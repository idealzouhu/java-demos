package com.zouhu.springboot.shardingsphere.entity;

/**
 * @author zouhu
 * @data 2024-11-18 17:29
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * 用户信息
 */
@Data
@AllArgsConstructor
public class User  implements Serializable {

    private static final long serialVersionUID = 1L;

    private int userId;

    private String username;
}
