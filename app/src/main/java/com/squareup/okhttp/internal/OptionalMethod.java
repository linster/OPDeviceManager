package com.squareup.okhttp.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class OptionalMethod {
    private final String methodName;
    private final Class[] methodParams;
    private final Class returnType;

    public OptionalMethod(Class cls, String str, Class... clsArr) {
        this.returnType = cls;
        this.methodName = str;
        this.methodParams = clsArr;
    }

    private Method getMethod(Class cls) {
        if (this.methodName == null) {
            return null;
        }
        Method publicMethod = getPublicMethod(cls, this.methodName, this.methodParams);
        return (publicMethod == null || this.returnType == null) ? publicMethod : this.returnType.isAssignableFrom(publicMethod.getReturnType()) ? publicMethod : null;
    }

    private static Method getPublicMethod(Class cls, String str, Class[] clsArr) {
        try {
            Method method = cls.getMethod(str, clsArr);
            try {
                return (method.getModifiers() & 1) != 0 ? method : null;
            } catch (NoSuchMethodException e) {
                return method;
            }
        } catch (NoSuchMethodException e2) {
            return null;
        }
    }

    public Object invoke(Object obj, Object... objArr) {
        Object method = getMethod(obj.getClass());
        if (method != null) {
            try {
                return method.invoke(obj, objArr);
            } catch (Throwable e) {
                AssertionError assertionError = new AssertionError("Unexpectedly could not call: " + method);
                assertionError.initCause(e);
                throw assertionError;
            }
        }
        throw new AssertionError("Method " + this.methodName + " not supported for object " + obj);
    }

    public Object invokeOptional(Object obj, Object... objArr) {
        Method method = getMethod(obj.getClass());
        if (method == null) {
            return null;
        }
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public Object invokeOptionalWithoutCheckedException(Object obj, Object... objArr) {
        try {
            return invokeOptional(obj, objArr);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw ((RuntimeException) targetException);
            }
            AssertionError assertionError = new AssertionError("Unexpected exception");
            assertionError.initCause(targetException);
            throw assertionError;
        }
    }

    public Object invokeWithoutCheckedException(Object obj, Object... objArr) {
        try {
            return invoke(obj, objArr);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw ((RuntimeException) targetException);
            }
            AssertionError assertionError = new AssertionError("Unexpected exception");
            assertionError.initCause(targetException);
            throw assertionError;
        }
    }

    public boolean isSupported(Object obj) {
        return getMethod(obj.getClass()) != null;
    }
}
