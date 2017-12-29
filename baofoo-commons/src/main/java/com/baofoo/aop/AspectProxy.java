package com.baofoo.aop;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class AspectProxy implements InvocationHandler, Serializable {

    private static final long serialVersionUID = -6424540398559729838L;

    private final Object obj;
    private final List<IAspectInterceptor> interceptors;

    public AspectProxy(Object obj, List<IAspectInterceptor> interceptors) {
        this.obj = obj;
        this.interceptors = interceptors;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        FakeJoinPoint jp = new FakeJoinPoint(obj, method, args);

        if (interceptors != null) {
            for (IAspectInterceptor interceptor : interceptors) {
                interceptor.before(jp);
            }
        }

        Object ret = null;
        boolean success = true;
        Exception ex = null;
        try {
            ret = method.invoke(obj, args);
        } catch (Exception e) {
            success = false;
            ex = e;

        }

        if (success) {
            if (interceptors != null) {
                for (IAspectInterceptor interceptor : interceptors) {
                    interceptor.afterReturning(jp);
                }
            }

            return ret;
        } else {
            if (interceptors != null) {
                for (IAspectInterceptor interceptor : interceptors) {
                    interceptor.afterThrowing(jp, ex);
                }
            }

            throw ex;
        }

    }

}
