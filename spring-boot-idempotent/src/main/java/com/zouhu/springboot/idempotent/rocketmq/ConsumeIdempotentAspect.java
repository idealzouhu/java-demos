package com.zouhu.springboot.idempotent.rocketmq;


import com.zouhu.springboot.idempotent.rocketmq.annotation.Idempotent;
import com.zouhu.springboot.idempotent.rocketmq.enums.IdempotentMQConsumeStatusEnum;
import com.zouhu.springboot.idempotent.rocketmq.toolkit.SpELUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 防止消息队列中消费者重复消费消息的切面实现类
 *
 * @author zouhu
 * @data 2024-09-18 22:35
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class ConsumeIdempotentAspect {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String LUA_SCRIPT =  """
            local key = KEYS[1]
            local value = ARGV[1]
            local expire_time_ms = ARGV[2]
            if redis.call('SET', key, value, 'NX', 'PX', expire_time_ms) then
                return nil
            else
                return redis.call('GET', key)
            end
            """;

    @Around("@annotation(com.zouhu.springboot.idempotent.rocketmq.annotation.Idempotent)")
    public Object idempotentHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取被代理方法上的特定注解信息
        Idempotent idempotent = getIdempotentAnnotation(joinPoint);

        // 生成全局唯一幂等标识
        String uniqueKey = idempotent.uniqueKeyPrefix() + SpELUtil.parseKey(idempotent.key(), ((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getArgs());
        System.out.println("uniqueKey: " + uniqueKey);

        // 获取 uniqueKey 在去重表里面的 value
        String absentAndGet = stringRedisTemplate.execute(
                RedisScript.of(LUA_SCRIPT, String.class),
                List.of(uniqueKey),
                IdempotentMQConsumeStatusEnum.CONSUMING.getCode(),
                String.valueOf(TimeUnit.SECONDS.toMillis(idempotent.keyTimeout()))
        );
        System.out.println("absentAndGet: " + absentAndGet);

        // 情况一: absentAndGet 不为空, 触发幂等处理逻辑, 说明消息正在消费中(absentAndGet=0)或者消费完成(absentAndGet=1)
        if (Objects.nonNull(absentAndGet)) {
            boolean errorFlag = IdempotentMQConsumeStatusEnum.isError(absentAndGet);
            log.warn("[{}] MQ 消息重复消费, {}", uniqueKey, errorFlag ? "客户端正在消费中" : "客户端已完成消费");

            // 消息正在消费中，抛出异常
            if (errorFlag) {
                throw new RuntimeException(String.format("消息消费者幂等异常，幂等标识：%s", uniqueKey));
            }
            return null;
        }

        // 情况二: absentAndGet 不为空, 不触发幂等处理逻辑,
        Object result;
        try {
            // 执行标记了消息队列防重复消费注解的方法
            result = joinPoint.proceed();

            // 设置防重令牌 Key 过期时间，标记为已消费
            stringRedisTemplate.opsForValue().set(uniqueKey, IdempotentMQConsumeStatusEnum.CONSUMED.getCode(), idempotent.keyTimeout(), TimeUnit.SECONDS);
        } catch (Throwable ex) {
            // 删除幂等 Key，让消息队列消费者重试逻辑进行重新消费
            stringRedisTemplate.delete(uniqueKey);
            throw ex;
        }
        return result;
    }


    /**
     * 获取被代理方法上的特定注解信息
     *
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    public static Idempotent getIdempotentAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName(), methodSignature.getMethod().getParameterTypes());
        return targetMethod.getAnnotation(Idempotent.class);
    }
}
