package org.mrbluesky.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class ControllerAspect {

  private final Logger logger =  LoggerFactory.getLogger(this.getClass());

  @Around(value = "execution(* org.mrbluesky.controller.*.get*(..))")
  public Object preprocessController(ProceedingJoinPoint pjp) throws Throwable {

    double startTime = System.currentTimeMillis();
    ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = servletContainer.getRequest();
    HttpServletResponse response = servletContainer.getResponse();
    logger.info("## request path variable: " + request.getRequestURI());
    logger.info("## request ip address: " + request.getRemoteHost());
    Object result = pjp.proceed();
    double endTime = System.currentTimeMillis();
    logger.info("Elapsed time " + (endTime - startTime) + " ms");
    return result;

  }

}
