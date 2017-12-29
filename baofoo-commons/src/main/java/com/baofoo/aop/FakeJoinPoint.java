package com.baofoo.aop;

import com.baofoo.exception.ServiceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;

import java.lang.reflect.Method;

public class FakeJoinPoint implements JoinPoint, JoinPoint.StaticPart {

    private Object obj;
    private Method method;
    private Object[] args;

    public FakeJoinPoint(Object obj, Method method, Object[] args) {
        this.obj = obj;
        this.method = method;
        this.args = args;
    }

    @Override
    public String toShortString() {
        throw new ServiceException("unsupported");
    }

    @Override
    public String toLongString() {
        throw new ServiceException("unsupported");
    }

    @Override
    public Object getThis() {
        return obj;
    }

    @Override
    public Object getTarget() {
        throw new ServiceException("unsupported");
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public Signature getSignature() {
        return new FakeSignature(method);
    }

    @Override
    public SourceLocation getSourceLocation() {
        throw new ServiceException("unsupported");
    }

    @Override
    public String getKind() {
        return ProceedingJoinPoint.METHOD_EXECUTION;
    }

    @Override
    public StaticPart getStaticPart() {
        return this;
    }

    public int getId() {
        throw new ServiceException("unsupported");
    }
}
