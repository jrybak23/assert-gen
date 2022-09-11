package com.github.jrybak23.assertgen.result.generator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

class MethodUtils {

    static Optional<Method> getMethod(Class<?> aClass, String methodName) {
        try {
            return Optional.of(aClass.getMethod(methodName));
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        }
    }


    public static Object invokeMethod(Object value, Method method) {
        try {
            return method.invoke(value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
