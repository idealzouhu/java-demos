package com.zouhu.aop.log;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.SystemClock;
import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 切面类，用于在方法执行前后打印日志
 *
 * @author zouhu
 * @data 2024-07-26 22:24
 */
@Aspect
@Component
public class ILogPrintAspect {
    /**
     * 环绕通知，在方法执行前后打印日志。
     * <p>
     *     该切面会拦截标记有{@link ILog}注解的类或方法，并在执行前后打印相关日志信息。
     * </p>
     *
     * @param joinPoint 切面连接点，代表被拦截的方法。
     * @return 返回被拦截方法的执行结果。
     * @throws Throwable 如果被拦截方法执行过程中抛出异常，则会抛出给调用者。
     */
    @Around("@within(com.zouhu.aop.log.ILog) || @annotation(com.zouhu.aop.log.ILog)")
    public Object printMLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录方法开始执行的时间
        long startTime = SystemClock.now();
        // 获取方法签名，用于日志记录
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取日志记录器
        Logger log = LoggerFactory.getLogger(methodSignature.getDeclaringType());
        // 记录方法开始执行的当前时间，用于日志输出
        String beginTime = DateUtil.now();
        // 方法执行结果，默认为null
        Object result = null;
        try {
            // 执行被拦截的方法
            result = joinPoint.proceed();
        } finally {
            // 获取目标方法和其上的ILog注解
            Method targetMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName(), methodSignature.getMethod().getParameterTypes());
            ILog logAnnotation = Optional.ofNullable(targetMethod.getAnnotation(ILog.class)).orElse(joinPoint.getTarget().getClass().getAnnotation(ILog.class));
            if (logAnnotation != null) {
                System.out.println("Method: " );
                // 准备日志打印信息
                ILogPrintDTO logPrint = new ILogPrintDTO();
                logPrint.setBeginTime(beginTime);
                // 如果注解标识需要打印输入参数，则构建并设置输入参数日志
                if (logAnnotation.input()) {
                    logPrint.setInputParams(buildInput(joinPoint));
                }
                // 如果注解标识需要打印输出参数，则设置方法执行结果
                if (logAnnotation.output()) {
                    logPrint.setOutputParams(result);
                }
                // 尝试获取当前请求的方法类型和URI，用于日志记录
                String methodType = "", requestURI = "";
                try {
                    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    assert servletRequestAttributes != null;
                    methodType = servletRequestAttributes.getRequest().getMethod();
                    requestURI = servletRequestAttributes.getRequest().getRequestURI();
                } catch (Exception ignored) {
                }
                // 打印日志
                log.info("[{}] {}, executeTime: {}ms, info: {}", methodType, requestURI, SystemClock.now() - startTime, JSON.toJSONString(logPrint));
            }
        }
        // 返回被拦截方法的执行结果
        return result;
    }


    /**
     * 构建输入参数数组，用于日志打印或其他处理。
     * 该方法会遍历给定的JoinPoint的参数数组，过滤掉HttpServletRequest和HttpServletResponse，
     * 对于byte数组和MultipartFile类型的参数，会替换为相应的字符串表示，其他类型的参数保持不变。
     *
     * @param joinPoint AOP中的 ProceedingJoinPoint 对象，表示当前执行的方法的所有参数。
     * @return 返回一个对象数组，其中不包含HttpServletRequest和HttpServletResponse对象，
     *         对于特定类型的参数（byte数组和MultipartFile），进行了相应的字符串转换。
     */
    private Object[] buildInput(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs(); // 获取方法的所有参数
        Object[] printArgs = new Object[args.length]; // 创建一个新数组，用于存放处理后的参数
        for (int i = 0; i < args.length; i++) {
            // 过滤掉HttpServletRequest和HttpServletResponse
            if ((args[i] instanceof HttpServletRequest) || args[i] instanceof HttpServletResponse) {
                continue;
            }
            // 对byte数组和MultipartFile类型进行特殊处理，其他类型直接复制
            if (args[i] instanceof byte[]) {
                printArgs[i] = "byte array";
            } else if (args[i] instanceof MultipartFile) {
                printArgs[i] = "file";
            } else {
                printArgs[i] = args[i];
            }
        }
        return printArgs; // 返回处理后的参数数组
    }
}
