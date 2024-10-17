package ir.dotin.softwaresystems.librarymanagement.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrimAspect {
    @Before("execution(* ir.dotin.softwaresystems.librarymanagement.controller..*(..))")
    public void trimStrings(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String) {
                args[i] = ((String) args[i]).trim();
            }
        }
    }
}
