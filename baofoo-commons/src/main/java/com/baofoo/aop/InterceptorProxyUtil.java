package com.baofoo.aop;

import java.lang.reflect.Proxy;
import java.util.List;

public class InterceptorProxyUtil {

    public static Object proxyObject(Object obj, Class<?> objectType,
                                     List<IAspectInterceptor> interceptors) {
        if (interceptors == null || interceptors.size() == 0) {
            return obj;
        }

        final AspectProxy aspectProxy = new AspectProxy(obj, interceptors);
        return Proxy.newProxyInstance(objectType.getClassLoader(),
                new Class[]{objectType}, aspectProxy);
    }

    public static Object proxyObject(Object obj,
                                     List<IAspectInterceptor> interceptors) {
        if (interceptors == null || interceptors.size() == 0) {
            return obj;
        }

        final AspectProxy aspectProxy = new AspectProxy(obj, interceptors);
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                new Class[]{obj.getClass()}, aspectProxy);
    }

}
