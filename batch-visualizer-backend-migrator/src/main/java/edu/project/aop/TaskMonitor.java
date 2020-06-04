package edu.project.aop;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Timed
public class TaskMonitor {

//    @AfterReturning("execution(* *edu.project.config.job.BatchVisualizerJobConfiguration.*(..))")
//    public void calculateProcessTime(JoinPoint joinPoint) throws Throwable {
//        log.info(">>>>>>>AOP END>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//    }

    @Around("execution(* *edu.project.config.job.BatchVisualizerJobConfiguration.batchVisualizerJob(..))")
    public Object calculateProcessTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object retVal = null;
        log.info(">>>>>>>AOP START>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        try {
            retVal = joinPoint.proceed();
            log.info(">>>>>>>AOP END>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        } catch (Exception e) {
            log.info("Exception Logic");
        }
        return retVal;
    }
}
