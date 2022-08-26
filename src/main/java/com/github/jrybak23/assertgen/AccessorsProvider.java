package com.github.jrybak23.assertgen;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.lang.reflect.Modifier.isPrivate;
import static java.lang.reflect.Modifier.isStatic;

public class AccessorsProvider {

    public Stream<Method> selectMethods(Class<?> classInstance) {
        return Stream.of(classInstance.getMethods(), classInstance.getDeclaredMethods())
                .flatMap(Arrays::stream)
                .distinct()
                .filter(this::isSuitableForAssertion);
    }

    private boolean isSuitableForAssertion(Method method) {
        return isNotPrivate(method)
                && hasZeroParameters(method)
                && isNotVoid(method)
                && isNotStatic(method)
                && isNotAnyOf(method, "wait", "toString", "clone", "getClass", "hashCode");
    }

    private static boolean isNotStatic(Method method) {
        return !isStatic(method.getModifiers());
    }

    private static boolean isNotAnyOf(Method method, String... methodNames) {
        return Arrays.stream(methodNames)
                .noneMatch(method.getName()::equals);
    }

    private static boolean hasZeroParameters(Method method) {
        return method.getParameterCount() == 0;
    }

    private static boolean isNotPrivate(Method method) {
        return !isPrivate(method.getModifiers());
    }

    private static boolean isNotVoid(Method method) {
        return !method.getReturnType().getName().equals("void");
    }

}
