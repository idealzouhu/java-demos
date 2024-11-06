package com.zouhu.springboot.convention.exception;

import com.zouhu.springboot.convention.code.IErrorCode;

/**
 * 远程服务调用异常
 *
 * @author zouhu
 * @data 2024-11-03 22:34
 */
public class RemoteException  extends AbstractException {
    public RemoteException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public RemoteException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "RemoteException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
