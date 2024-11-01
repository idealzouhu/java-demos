package com.zouhu.strategy.pattern.init;

import org.springframework.context.ApplicationEvent;

/**
 * 应用初始化事件
 *
 * @author zouhu
 * @data 2024-10-31 16:33
 */
public class ApplicationInitializingEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public ApplicationInitializingEvent(Object source) {
        super(source);
    }
}
