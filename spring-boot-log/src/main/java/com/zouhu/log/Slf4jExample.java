package com.zouhu.log;

import org.slf4j.Logger;

/**
 * 测试 slf4j 的使用
 *
 * @author zouhu
 * @data 2024-08-01 17:01
 */
public class Slf4jExample {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Slf4jExample.class);

    public void example() {
        logger.trace("This is a trace message");
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warning message");
        logger.error("This is an error message");
    }

    public static void main(String[] args) {
        // INFO 级别以下的日志需要配置才能输出
        Slf4jExample example = new Slf4jExample();
        example.example();
    }

}
