package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.NameGenerator;
import com.github.jrybak23.assertgen.ReflectionUtils;
import com.github.jrybak23.assertgen.call.experession.CallExpression;
import com.github.jrybak23.assertgen.value.converter.ValueCodeConverterService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.Spliterator.ORDERED;

@RequiredArgsConstructor
@Setter
class MapResultGenerator implements ResultGenerator {

    private final ValueCodeConverterService valueCodeConverterService;
    private final NameGenerator nameGenerator;
    private ResultGeneratorProvider resultGeneratorProvider;

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof Map<?, ?>;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, CallExpression callExpression, Object value) {
        var map = (Map<?, ?>) value;
        if (map.isEmpty()) {
            codeAppender.appendNewLine("assertThat(" + callExpression + ").isEmpty();");
        } else {
            Set<? extends Map.Entry<?, ?>> entrySet = map.entrySet();
            var entries = new ArrayList<>(entrySet);
            if (isAllKeysCanBeConverted(entries) && isNotOrdered(map)) {
                codeAppender.appendNewLine("assertThat(" + callExpression + ")");
                codeAppender.sameIndent(() -> {
                    codeAppender.appendNewLine(".hasSize(" + map.size() + ")");
                    String valueName = generateNameForValue(map);
                    for (int i = 0; i < entries.size(); i++) {
                        Map.Entry<?, ?> entry = entries.get(i);
                        String convertedKey = valueCodeConverterService.convertValueToCode(entry.getKey()).orElseThrow();
                        codeAppender.appendNewLine(".hasEntrySatisfying(" + convertedKey + ", " + valueName + " -> {");
                        codeAppender.sameIndent(() -> {
                            resultGeneratorProvider.findSuitable(entry.getValue())
                                    .generateCode(codeAppender, CallExpression.ofReference(valueName), entry.getValue());
                        });
                        if (i == entries.size() - 1) {
                            codeAppender.appendNewLine("});");
                        } else {
                            codeAppender.appendNewLine("})");
                        }
                    }
                });
            } else {
                resultGeneratorProvider.findSuitable(entrySet)
                        .generateCode(codeAppender, callExpression.addMethodCall(getEntrySetMethod()), entrySet);
            }
        }
    }

    private String generateNameForValue(Map<?, ?> map) {
        List<? extends Class<?>> classes = map.values().stream()
                .filter(Objects::nonNull)
                .map(Object::getClass)
                .toList();

        Class<?> aClass = ReflectionUtils.getCommonSuperClasses(classes).get(0);
        if (aClass.equals(Object.class)) {
            return "value";
        }
        return nameGenerator.generateItemName(aClass.getSimpleName());
    }

    private boolean isAllKeysCanBeConverted(List<? extends Map.Entry<?, ?>> entries) {
        return entries.stream()
                .map(Map.Entry::getKey)
                .allMatch(valueCodeConverterService::canConvert);
    }

    private static boolean isNotOrdered(Map<?, ?> value) {
        return !value.entrySet().spliterator().hasCharacteristics(ORDERED);
    }

    private static Method getEntrySetMethod() {
        try {
            return Map.class.getMethod("entrySet");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
