package com.zrytech.framework.app.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.io.IOException;

@Aspect
@Component
public class AopParameterValidationHandler {

    @Pointcut("@annotation(javax.validation.Valid)")

    public void pointCut() {
    }

    @Before("pointCut()")
    public void beforeLog(JoinPoint joinPoint) throws IOException {
        Object[] args = joinPoint.getArgs();
        for (Object object : args) {
            if (object instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) object;
                if (bindingResult.getErrorCount() > 0) {
                    ObjectError objectError = bindingResult.getAllErrors().get(0);
                    throw new RuntimeException(objectError.getDefaultMessage());
                }
                break;
            }
        }
    }

}
