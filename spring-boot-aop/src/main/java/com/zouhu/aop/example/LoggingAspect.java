package com.zouhu.aop.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 *  使用Spring AOP实现环绕通知
 *
 * @author zouhu
 * @data 2024-07-26 21:10
 */
@Aspect
@Component  // 确保切面类被 Spring 管理
public class LoggingAspect {
    /**
     * 环绕通知
     *
     * @param joinPoint
     * @return 方法执行结果
     * @throws Throwable
     */
    @Around("execution(* com.zouhu.aop.example.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            // 在方法调用前执行的操作
            System.out.println("Before method: " + joinPoint.getSignature().getName());
            // 调用目标方法
            Object result = joinPoint.proceed();
            // 在方法调用后执行的操作
            System.out.println("After method: " + joinPoint.getSignature().getName());
            return result;
        } finally {
            // 无论是否抛出异常都会执行的操作
            long elapsedTime = System.currentTimeMillis() - start;
            System.out.println("Execution time: " + elapsedTime + "ms");
        }
    }
}

