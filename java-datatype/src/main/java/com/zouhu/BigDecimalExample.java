package com.zouhu;

import java.math.BigDecimal;

/**
 * 测试 BigDecimal 类
 *
 * @author zouhu
 * @data 2024-08-27 15:28
 */
public class BigDecimalExample {
    public static void main(String[] args) {
        // 测试浮点数精度问题
        // double a = 0.1 + 0.2;
        // System.out.println(a);  // 输出: 0.30000000000000004

        // 使用 BigDecimal 进行精确计算
        BigDecimal a = new BigDecimal("0.1");
        BigDecimal b = new BigDecimal("0.2");
        BigDecimal sum = a.add(b);
        System.out.println(sum);  // 输出: 0.3
    }
}
