package com.task.inventory_api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
@EnableAsync
public class MethodRunTimeCheckAspect {
    private StopWatch stopWatch;

    /**
     * GET Controller의 시간체크 annotation
     * @param joinPoint
     * @param methodRunTimeCheck
     * @return
     * @throws Throwable
     */
    @Around("@annotation(methodRunTimeCheck)")
    private Object around(final ProceedingJoinPoint joinPoint, final InventoryAnnotation.MethodRunTimeCheck methodRunTimeCheck) throws Throwable {

        String methodName = methodRunTimeCheck.name();
        if(StringUtils.isBlank(methodRunTimeCheck.name())){
            methodName = joinPoint.getSignature().getName();
        }
        StopWatch stopWatch = new StopWatch(methodName);
        Object proceed = null;

        try{
            stopWatch.start();
            proceed = joinPoint.proceed();
        } finally {
            stopWatch.stop();
            log.info("{} total second =====> {}", methodName, (stopWatch.getTotalTimeMillis()/1000));
            log.info("{}",stopWatch.prettyPrint());
        }

        return proceed;
    }
}
