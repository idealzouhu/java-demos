package com.zouhu.springboot.convention.exception;

import com.zouhu.springboot.convention.code.BaseErrorCode;
import com.zouhu.springboot.convention.code.IErrorCode;

/**
 *  客户端异常
 *
 * @author zouhu
 * @data 2024-11-03 22:29
 */
public class ClientException  extends AbstractException {
    public ClientException(IErrorCode errorCode) {
        this(null, null, errorCode);
    }

    public ClientException(String message) {
        this(message, null, BaseErrorCode.CLIENT_ERROR);
    }

    public ClientException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ClientException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
