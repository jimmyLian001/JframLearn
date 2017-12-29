package com.baofoo.aop;

import org.aspectj.lang.Signature;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class FakeSignature implements Signature {

    private Method method;

    public FakeSignature(Method method) {
        this.method = method;
    }

    @Override
    public String toShortString() {
        return toString(false, false, false, false);
    }

    @Override
    public String toLongString() {
        return toString(true, true, true, true);
    }

    @Override
    public String getName() {
        return method.getName();
    }

    @Override
    public int getModifiers() {
        return method.getModifiers();
    }

    @Override
    public Class<?> getDeclaringType() {
        return method.getDeclaringClass();
    }

    @Override
    public String getDeclaringTypeName() {
        return method.getDeclaringClass().getName();
    }

    public Class<?> getReturnType() {
        return method.getReturnType();
    }

    public Class<?>[] getParameterTypes() {
        return method.getParameterTypes();
    }

    @Override
    public String toString() {
        return toString(false, true, false, true);
    }

    private String toString(boolean includeModifier,
                            boolean includeReturnTypeAndArgs,
                            boolean useLongReturnAndArgumentTypeName, boolean useLongTypeName) {
        StringBuilder sb = new StringBuilder();
        if (includeModifier) {
            sb.append(Modifier.toString(getModifiers()));
            sb.append(" ");
        }
        if (includeReturnTypeAndArgs) {
            appendType(sb, getReturnType(), useLongReturnAndArgumentTypeName);
            sb.append(" ");
        }
        appendType(sb, getDeclaringType(), useLongTypeName);
        sb.append(".");
        sb.append(method.getName());
        sb.append("(");
        Class<?>[] parametersTypes = getParameterTypes();
        appendTypes(sb, parametersTypes, includeReturnTypeAndArgs,
                useLongReturnAndArgumentTypeName);
        sb.append(")");
        return sb.toString();
    }

    private void appendTypes(StringBuilder sb, Class<?>[] types,
                             boolean includeArgs, boolean useLongReturnAndArgumentTypeName) {
        if (includeArgs) {
            for (int size = types.length, i = 0; i < size; i++) {
                appendType(sb, types[i], useLongReturnAndArgumentTypeName);
                if (i < size - 1) {
                    sb.append(",");
                }
            }
        } else {
            if (types.length != 0) {
                sb.append("..");
            }
        }
    }

    private void appendType(StringBuilder sb, Class<?> type,
                            boolean useLongTypeName) {
        if (type.isArray()) {
            appendType(sb, type.getComponentType(), useLongTypeName);
            sb.append("[]");
        } else {
            sb.append(useLongTypeName ? type.getName() : type.getSimpleName());
        }
    }
}
