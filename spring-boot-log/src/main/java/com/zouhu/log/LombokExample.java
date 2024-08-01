package com.zouhu.log;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试 Lombok 的@slf4j 注解
 *
 * @author zouhu
 * @data 2024-08-01 17:06
 */
@Slf4j
public class LombokExample {
    public void example()
    {
        log.trace("This is a trace message");
        log.debug("This is a debug message");
        log.info("This is an info message");
        log.warn("This is a warning message");
        log.error("This is an error message");
    }

    public static void main(String[] args)
    {
        // INFO 级别以下的日志需要配置才能输出
        LombokExample lombokExample = new LombokExample();
        lombokExample.example();
    }
}
