package com.zouhu.aop.log;

import lombok.Data;

/**
 * ILog 日志打印实体
 *
 * @author zouhu
 * @data 2024-07-26 22:23
 */
@Data
public class ILogPrintDTO {
    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 请求入参
     */
    private Object[] inputParams;

    /**
     * 返回参数
     */
    private Object outputParams;
}
