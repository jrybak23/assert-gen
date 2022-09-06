package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.call.experession.CallExpression;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

@Setter
class OptionalPrimitiveResultGenerator implements ResultGenerator {

    private ResultGeneratorProvider resultGeneratorProvider;

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof OptionalInt
                || value instanceof OptionalLong
                || value instanceof OptionalDouble;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, CallExpression callExpression, Object value) {
        Method isEmptyMethod = getMethod(value.getClass(), "isEmpty");
        boolean isEmpty = (boolean) invokeMethod(value, isEmptyMethod);
        if (isEmpty) {
            codeAppender.appendNewLine("assertThat(" + callExpression + ").isEmpty();");
        } else {
            Method orElseThrowMethod = getMethod(value.getClass(), "orElseThrow");
            Object object = invokeMethod(value, orElseThrowMethod);
            resultGeneratorProvider.findSuitable(object)
                    .generateCode(codeAppender, callExpression.addMethodCall(orElseThrowMethod), object);
        }
    }

    private static Object invokeMethod(Object value, Method method) {
        try {
           return method.invoke(value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Method getMethod(Class<?> aClass, String methodName) {
        try {
            return aClass.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
