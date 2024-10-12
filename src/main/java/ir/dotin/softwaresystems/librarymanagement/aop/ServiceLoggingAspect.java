package ir.dotin.softwaresystems.librarymanagement.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Configuration
@Component
public class ServiceLoggingAspect
{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // اجرای logging قبل از اجرای متدهای سرویس
    @Before("execution(* ir.dotin.softwaresystems.librarymanagement.service.*.*(..))")
    public void logBeforeServiceMethods(JoinPoint joinPoint) {
        logger.info("Before execution of {}",joinPoint);
    }

    // اجرای logging بعد از اجرای متدهای سرویس
    @After("execution(* ir.dotin.softwaresystems.librarymanagement.service.*.*(..))")
    public void logAfterServiceMethods() {
        log.info("A method in service layer has just finished execution");
    }


    // اجرای logging قبل و بعد از اجرای متدهای سرویس با مدیریت زمان
    @Around("execution(* ir.dotin.softwaresystems.librarymanagement.service.*.*(..))")
    public Object logAroundServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.info("Method " + joinPoint.getSignature().getName() + " is about to execute");

        Object result = joinPoint.proceed();  // اجرای متد اصلی

        long endTime = System.currentTimeMillis();
        log.info("Method " + joinPoint.getSignature().getName() + " executed in " + (endTime - startTime) + " ms");

        return result;
    }
}

