package com.zouhu.springboot.idempotent.redislock;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSON;
import com.zouhu.springboot.idempotent.redislock.annotation.Idempotent;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 * 防止用户重复提交的切面实现类
 * <p>
 *     分布式锁的 Key 由以下 4 部分组成：
 *     1. Idempotent：前缀，用于区分不同业务；
 *     1. path：请求路径，用于区分不同接口；
 *     2. currentUserId：当前用户id，用于区分不同用户；
 *     3. argsMD5：参数的 MD5 值，用于区分不同参数；
 * </p>
 *
 * @author zouhu
 * @data 2024-09-18 19:35
 */
@Aspect
@RequiredArgsConstructor
public class SubmitIdempotentAspect {

    private final RedissonClient redissonClient;

    @Around("@annotation(com.zouhu.springboot.idempotent.redislock.annotation.Idempotent)")
    public Object idempotentHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取被代理方法上的特定注解信息
        Idempotent idempotent = getIdempotentAnnotation(joinPoint);

        // 获取分布式锁标识
        String lockKey = String.format("Idempotent:path:%s:currentUserId:%s:md5:%s", getServletPath(), getCurrentUserId(), calcArgsMD5(joinPoint));
        RLock lock = redissonClient.getLock(lockKey);

        // 尝试获取锁，获取锁失败就意味着已经重复提交，直接抛出异常
        if (!lock.tryLock()) {
            throw new RuntimeException(idempotent.message());
        }

        // 获取锁成功，执行原逻辑
        Object result;
        try {
            // 执行标记了防重复提交注解的方法原逻辑
            result = joinPoint.proceed();
            System.out.println(result);
        } finally {
            lock.unlock();
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

    /**
     * 获取请求路径
     *
     * @return 请求路径
     */
    private String getServletPath() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest().getServletPath();
    }

    /**
     * 获取当前用户id
     *
     * @return 当前用户id
     */
    private String getCurrentUserId() {
        // TODO 从上下文获取当前用户id
        return "001";
    }

    /**
     * 计算方法参数的MD5值
     *
     * @param joinPoint 切入点对象，包含调用方法的信息以及传入的参数
     * @return 返回参数的MD5值字符串
     */
    private String calcArgsMD5(ProceedingJoinPoint joinPoint) {
        return DigestUtil.md5Hex(JSON.toJSONBytes(joinPoint.getArgs()));
    }

}
