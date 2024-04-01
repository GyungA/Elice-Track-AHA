package com.secondproject.shoppingproject.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Aspect
@Component
@Slf4j
public class ExceptionHandlerAspect {
    @Pointcut("execution(* com.secondproject.shoppingproject.*.service.*.*(..))")
    public void allServiceMethods() {
    }

    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "ex")
    public void handleNumberFormatException(NoSuchElementException ex) {
        log.warn("Wrong NoSuchElementException Occurred! " + ex.getMessage());
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
