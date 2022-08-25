package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.reflect.Modifier.isPrivate;
import static java.lang.reflect.Modifier.isStatic;

@Setter
public class ObjectResultGenerator implements ResultGenerator {

    private ResultGeneratorProvider resultGeneratorProvider;

    @Override
    public boolean isSuitable(Object value) {
        return true;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, String code, Object object) {
        Class<?> classInstance = object.getClass();
        Method[] methods = classInstance.getMethods();
        for (Method method : methods) {
            if (isSuitableForAssertion(method)) {
                String methodName = method.getName();
                Object value = invokeMethod(method, object);
                ResultGenerator generator = resultGeneratorProvider.findSuitable(value);
                String methodCall = "." + methodName + "()";
                generator.generateCode(codeAppender, code + methodCall, value);
            }
        }
    }

    private Object invokeMethod(Method method, Object inputObject) {
        method.setAccessible(true);
        try {
            return method.invoke(inputObject);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isSuitableForAssertion(Method method) {
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
