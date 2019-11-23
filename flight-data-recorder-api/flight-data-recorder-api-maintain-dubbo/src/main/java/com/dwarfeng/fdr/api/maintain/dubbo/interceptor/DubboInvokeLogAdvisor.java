package com.dwarfeng.fdr.api.maintain.dubbo.interceptor;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyseAdvisor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 调用Dubbo代理方法时记录日志的增强。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@Component
@Aspect
public class DubboInvokeLogAdvisor {

    private final Logger LOGGER = LoggerFactory.getLogger(TimeAnalyseAdvisor.class);

    @Around("@annotation(com.dwarfeng.fdr.api.maintain.dubbo.interceptor.DubboInvokeLog) || @within(com.dwarfeng.fdr.api.maintain.dubbo.interceptor.DubboInvokeLog)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        LOGGER.debug("方法 " + className + "." + methodName + " 开始调用dubbo代理...");
        return pjp.proceed(pjp.getArgs());
    }
}
