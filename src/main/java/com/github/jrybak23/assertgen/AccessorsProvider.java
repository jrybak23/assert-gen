package com.github.jrybak23.assertgen;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.reflect.Modifier.isPrivate;
import static java.lang.reflect.Modifier.isStatic;

public class AccessorsProvider {

    private static final Comparator<Method> METHOD_COMPARATOR = Comparator.comparing(Method::getName);

    public Set<Method> selectMethods(Class<?> classInstance) {
        Set<Method> result = new TreeSet<>(METHOD_COMPARATOR);
        Class<?> currentClass = classInstance;
        do {
            addMethodsFromClass(currentClass, result);
            for (Class<?> anInterface : currentClass.getInterfaces()) {
                addMethodsFromClass(anInterface, result);
            }
            currentClass = currentClass.getSuperclass();
        } while (currentClass != null);
        return result;
    }

    private void addMethodsFromClass(Class<?> currentClass, Set<Method> result) {
        for (Method declaredMethod : currentClass.getDeclaredMethods()) {
            if (isSuitableForAssertion(declaredMethod) && declaredMethod.trySetAccessible()) {
                result.add(declaredMethod);
            }
        }
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
