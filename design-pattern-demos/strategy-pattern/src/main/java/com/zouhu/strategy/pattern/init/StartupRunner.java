package com.zouhu.strategy.pattern.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 定义一个在应用启动后执行的组件，主动发布 ApplicationInitializingEvent
 * <p>
 *     在使用测试代码时，这个 ApplicationInitializingEvent 没有发布。因此，主动执行发布该事件。
 *     如果正常启动项目的话，该函数可以注释掉了。
 *     在 Spring Boot 中，ApplicationRunner 和 CommandLineRunner 都是 Spring Boot 提供的用于在应用启动后执行的组件，但是它们之间有一些区别：
 * </p>
 *
 * @author zouhu
 * @data 2024-11-01 15:39
 */
@Component
public class StartupRunner implements CommandLineRunner {
    // 应用事件发布器，用于发布应用生命周期中的事件
    private final ApplicationEventPublisher eventPublisher;

    public StartupRunner(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void run(String... args) throws Exception {
        // 发布自定义的应用初始化事件
        eventPublisher.publishEvent(new ApplicationInitializingEvent(this));
    }
}
