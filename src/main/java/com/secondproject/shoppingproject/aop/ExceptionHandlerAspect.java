package com.secondproject.shoppingproject.aop;

import com.secondproject.shoppingproject.global.exception.BadRequestException;
import com.secondproject.shoppingproject.global.exception.ConflictException;
import com.secondproject.shoppingproject.global.exception.ForbiddenException;
import com.secondproject.shoppingproject.global.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
@Slf4j
public class ExceptionHandlerAspect {
    @Pointcut("execution(* com.secondproject.shoppingproject.*.service.*.*(..))")
    public void allServiceMethods() {
    }

    //400
    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "ex")
    public void BadRequestException(BadRequestException ex) {
        log.warn("Wrong BadRequestException Occurred! " + ex.getMessage());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    //403
    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "ex")
    public void ForbiddenException(ForbiddenException ex) {
        log.warn("Wrong ForbiddenException Occurred! " + ex.getMessage());
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    //404
    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "ex")
    public void NotFoundException(NotFoundException ex) {
        log.warn("Wrong NotFoundException Occurred! " + ex.getMessage());
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //409
    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "ex")
    public void ConflictException(ConflictException ex) {
        log.warn("Wrong ConflictException Occurred! " + ex.getMessage());
        throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
    }
}
