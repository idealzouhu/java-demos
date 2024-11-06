package com.zouhu.springboot.convention.controller;

import com.zouhu.springboot.convention.code.BaseErrorCode;
import com.zouhu.springboot.convention.exception.ClientException;
import com.zouhu.springboot.convention.exception.RemoteException;
import com.zouhu.springboot.convention.exception.ServiceException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 测试全局异常处理
 *
 * @author zouhu
 * @data 2024-11-06 16:25
 */
@RestController
@RequestMapping("/test")
public class ExceptionTestController {
    @GetMapping("/client")
    public void throwClientException() {
        throw new ClientException(BaseErrorCode.USER_REGISTER_ERROR);
    }

    @GetMapping("/server")
    public void throwServerException() {
        // throw new ServiceException("服务端异常", BaseErrorCode.SERVICE_ERROR);
        throw new ServiceException("服务端异常");
    }

    @GetMapping("/thirdparty")
    public void throwThirdPartyException() {
        throw new RemoteException("第三方服务异常", BaseErrorCode.REMOTE_ERROR);
    }

    @GetMapping("/unknown")
    public void throwUnknownException() {
        throw new RuntimeException("Unknown error");
    }
}
