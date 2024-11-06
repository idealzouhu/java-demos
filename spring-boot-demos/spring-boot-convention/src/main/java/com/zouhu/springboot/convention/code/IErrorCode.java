package com.zouhu.springboot.convention.code;

/**
 *  错误码接口
 *
 */
public interface IErrorCode {
    /**
     * 错误码
     */
    String code();

    /**
     * 错误信息
     */
    String message();
}
