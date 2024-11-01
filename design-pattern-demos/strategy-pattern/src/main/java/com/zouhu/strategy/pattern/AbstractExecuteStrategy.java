package com.zouhu.strategy.pattern;

/**
 * 策略执行抽象接口
 *
 */
public interface AbstractExecuteStrategy<REQUEST, RESPONSE>  {
    /**
     * 执行策略标识
     * 用于标识具体的执行策略
     *
     */
    default String mark() {
        return null;
    }

    /**
     * 执行策略范匹配标识
     * 用于在多个策略中通过模式匹配选择合适的策略执行
     *
     */
    default String patternMatchMark() {
        return null;
    }

    /**
     * 执行策略
     *
     * @param requestParam 执行策略所需的参数，类型为REQUEST
     */
    default void execute(REQUEST requestParam) {

    }

    /**
     * 执行策略，带有返回值
     *
     * @param requestParam 执行策略所需的参数，类型为REQUEST
     * @return 执行策略后返回值，类型为RESPONSE
     */
    default RESPONSE executeResp(REQUEST requestParam) {
        return null;
    }
}
