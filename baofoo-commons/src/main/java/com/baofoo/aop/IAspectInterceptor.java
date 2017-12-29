package com.baofoo.aop;

import org.aspectj.lang.JoinPoint;

public interface IAspectInterceptor {

    public void before(JoinPoint jp);

    public void afterReturning(JoinPoint jp);

    public void afterThrowing(JoinPoint jp, Exception e);
}
