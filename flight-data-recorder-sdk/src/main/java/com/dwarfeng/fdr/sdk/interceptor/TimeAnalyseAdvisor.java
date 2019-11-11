package com.dwarfeng.fdr.sdk.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 运行时间分析增强。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@Component
@Aspect
public class TimeAnalyseAdvisor {

    /**
     * 时间分析注解。
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    public @interface TimeAnalyse {

    }

    private final Logger LOGGER = LoggerFactory.getLogger(TimeAnalyseAdvisor.class);

    @Around("@annotation(com.dwarfeng.fdr.sdk.interceptor.TimeAnalyseAdvisor.TimeAnalyse) || @within(com.dwarfeng.fdr.sdk.interceptor.TimeAnalyseAdvisor.TimeAnalyse)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.debug("TimeAnalyseAspect: 进入增强方法");
        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        LOGGER.debug("TimeAnalyseAspect: 获得方法名称 " + methodName);
        LOGGER.info("TimeAnalyseAspect: 方法 " + className + "." + methodName + " 开始计时...");
        long firstTimeStamp = System.currentTimeMillis();
        LOGGER.debug("TimeAnalyseAspect: 获得当前系统时间戳 " + firstTimeStamp);
        try {
            LOGGER.debug("TimeAnalyseAspect: 原始方法执行");
            return pjp.proceed(pjp.getArgs());
        } finally {
            LOGGER.debug("TimeAnalyseAspect: 原始方法执行结束");
            long lastTimeStamp = System.currentTimeMillis();
            LOGGER.debug("TimeAnalyseAspect: 获得当前系统时间戳 " + lastTimeStamp);
            LOGGER.debug("TimeAnalyseAspect: 计算方法执行时间，公式: " + lastTimeStamp + "-" + firstTimeStamp);
            LOGGER.info("TimeAnalyseAspect: 方法 " + className + "." + methodName + " 运行结束，用时 " + (lastTimeStamp - firstTimeStamp) + " 毫秒");
        }
    }


}
