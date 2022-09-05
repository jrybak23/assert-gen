package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.AccessorsProvider;
import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.call.experession.CallExpression;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.temporal.Temporal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Setter
public class ObjectResultGenerator implements ResultGenerator {

    private final Map<Method, Integer> methodCalls = new HashMap<>();

    private static final Comparator<Map.Entry<Method, Object>> COMPARATOR = Comparator.comparing(entry -> {
        Object value = entry.getValue();
        List<Class<?>> firstPriorityClasses = List.of(
                CharSequence.class,
                Number.class,
                Enum.class,
                Temporal.class
        );

        for (int i = 0; i < firstPriorityClasses.size(); i++) {
            if (firstPriorityClasses.get(i).isInstance(value)) {
                return i;
            }
        }

        List<Class<?>> secondPriorityClasses = List.of(
                byte[].class,
                short[].class,
                int[].class,
                long[].class,
                float[].class,
                double[].class,
                boolean[].class,
                char[].class,
                Object[].class,
                Map.class,
                List.class
        );

        for (int i = 0; i < secondPriorityClasses.size(); i++) {
            if (secondPriorityClasses.get(i).isInstance(value)) {
                return i + firstPriorityClasses.size() + 2;
            }
        }

        return firstPriorityClasses.size() + 1; // unknown type objects have priority between first and second.
    });

    private ResultGeneratorProvider resultGeneratorProvider;
    private final AccessorsProvider accessorsProvider;

    @Override
    public boolean isSuitable(Object value) {
        return true;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, CallExpression callExpression, Object object) {
        Class<?> classInstance = object.getClass();
        accessorsProvider.selectMethods(classInstance)
                .filter(AccessibleObject::trySetAccessible)
                .map(method -> {
                    Object value = invokeMethod(method, object);
                    return Map.entry(method, value);
                })
                .sorted(COMPARATOR)
                .forEach(methodAndValue -> {
                    Method method = methodAndValue.getKey();
                    Integer calls = methodCalls.getOrDefault(method, 0);
                    if (calls == 0) {
                        Object value = methodAndValue.getValue();
                        methodCalls.put(method, calls + 1);
                        resultGeneratorProvider.findSuitable(value)
                                .generateCode(codeAppender, callExpression.addMethodCall(method.getName()), value);
                    }
                });
    }

    private Object invokeMethod(Method method, Object inputObject) {
        try {
            return method.invoke(inputObject);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
