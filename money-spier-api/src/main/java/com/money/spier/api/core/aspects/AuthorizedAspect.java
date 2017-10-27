package com.money.spier.api.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AuthorizedAspect {

  @Pointcut("@annotation(Authorized)")
  public void isUserAuthorized() {}

  @Before("isUserAuthorized()")
  public void process(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
  }

}
