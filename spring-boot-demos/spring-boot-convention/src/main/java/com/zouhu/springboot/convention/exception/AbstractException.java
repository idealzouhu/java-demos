package com.zouhu.springboot.convention.exception;

import com.zouhu.springboot.convention.code.IErrorCode;
import lombok.Getter;
import org.springframework.util.StringUtils;


import java.util.Optional;

/**
 * 抽象异常
 * <p>
 *     抽象异常体系中的客户端异常、服务端异常以及远程服务调用异常
 * </p>
 *
 * @see ClientException
 * @see ServiceException
 * @see RemoteException
 */
@Getter
public abstract class AbstractException extends RuntimeException  {
    public final String errorCode;

    public final String errorMessage;

    public AbstractException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable);
        this.errorCode = errorCode.code();
        this.errorMessage = Optional.ofNullable(StringUtils.hasLength(message) ? message : null).orElse(errorCode.message());
    }
}
