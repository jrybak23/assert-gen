package com.github.jrybak23.assertgen.value.converter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.stream.Stream;

class ParsableTemporalValueCodeConverter implements ValueCodeConverter {

    @Override
    public boolean isSuitableFor(Object value) {
        return value instanceof Temporal && hasParseMethod(value.getClass());
    }

    private boolean hasParseMethod(Class<?> aClass) {
        return Stream.of(aClass.getMethods(), aClass.getDeclaredMethods())
                .flatMap(Arrays::stream)
                .anyMatch(method -> isParseMethod(method) && hasOneStringParam(method) && isStatic(method));
    }

    private static boolean isParseMethod(Method method) {
        return method.getName().equals("parse");
    }

    private static boolean hasOneStringParam(Method method) {
        return method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(CharSequence.class);
    }

    private static boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    @Override
    public String convert(Object value) {
        return value.getClass().getSimpleName() + ".parse(\"" + value + "\")";
    }
}
