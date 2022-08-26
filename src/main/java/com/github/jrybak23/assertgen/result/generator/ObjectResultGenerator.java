package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.AccessorsProvider;
import com.github.jrybak23.assertgen.CodeAppender;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Setter
public class ObjectResultGenerator implements ResultGenerator {

    public static final Comparator<Map.Entry<Method, Object>> COMPARATOR = Comparator.comparing(entry -> {
        Object value = entry.getValue();
        List<Class<? extends Serializable>> firstPriorityClasses = List.of(
                String.class,
                Number.class,
                Enum.class,
                LocalDate.class,
                LocalDateTime.class
        );

        for (int i = 0; i < firstPriorityClasses.size(); i++) {
            if (firstPriorityClasses.get(i).isInstance(value)) {
                return i;
            }
        }

        List<Class<?>> secondPriorityClasses = List.of(
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
    public void generateCode(CodeAppender codeAppender, String code, Object object) {
        Class<?> classInstance = object.getClass();
        accessorsProvider.selectMethods(classInstance)
                .map(method -> {
                    Object value = invokeMethod(method, object);
                    return Map.entry(method, value);
                })
                .sorted(COMPARATOR)
                .forEach(methodAndValue -> {
                    String methodName = methodAndValue.getKey().getName();
                    Object value = methodAndValue.getValue();
                    String methodCall = "." + methodName + "()";
                    resultGeneratorProvider.findSuitable(value)
                            .generateCode(codeAppender, code + methodCall, value);
                });
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
}
