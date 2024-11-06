package com.zouhu.springboot.convention.exception;

import com.zouhu.springboot.convention.code.BaseErrorCode;
import com.zouhu.springboot.convention.code.IErrorCode;

/**
 * 服务端异常
 *
 * @author zouhu
 * @data 2024-11-03 22:34
 */
public class ServiceException  extends AbstractException {
    public ServiceException(String message) {
        this(message, null, BaseErrorCode.SERVICE_ERROR);
    }

    public ServiceException(IErrorCode errorCode) {
        this(null, errorCode);
    }

    public ServiceException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ServiceException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
