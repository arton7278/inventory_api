package com.task.inventory_api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.info("handleUncaughtException =====>");
        ex.printStackTrace();
        log.info("Async 처리 error 발생 : Method Name {} ", method.getName());
        log.info("Message : {} ", ex);
    }

}
