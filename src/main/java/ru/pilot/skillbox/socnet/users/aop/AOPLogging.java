package ru.pilot.skillbox.socnet.users.aop;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AOPLogging {
    @Around("@annotation(ru.pilot.skillbox.socnet.users.aop.Loggable)")
    public Object measureMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        String params = Arrays.stream(pjp.getArgs())
                .map(arg -> arg+"")
                .collect(Collectors.joining("; "));
        
        log.debug("CALL START: {}, params: {}", methodName, params);
        try {
            Object proceed = pjp.proceed();
            log.debug("CALL END  : {}, result: {}", methodName, proceed+"");
            return proceed;
        } catch (Throwable t){
            log.error("CALL ERROR: " + methodName + ", error: " + t.getMessage(), t);
            throw t;
        }
    }
}
