package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.NameGenerator;
import com.github.jrybak23.assertgen.call.experession.CallExpression;
import com.github.jrybak23.assertgen.value.converter.ValueCodeConverterService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
@Setter
class IterableAndArrayResultGenerator implements ResultGenerator {

    private final ValueCodeConverterService valueCodeConverterService;
    private final NameGenerator nameGenerator;
    private ResultGeneratorProvider resultGeneratorProvider;

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof Iterable<?> || isArray(value);
    }

    private boolean isArray(Object value) {
        return value instanceof Object[]
                || value instanceof byte[]
                || value instanceof short[]
                || value instanceof int[]
                || value instanceof long[]
                || value instanceof float[]
                || value instanceof double[]
                || value instanceof boolean[]
                || value instanceof char[];
    }

    @Override
    public void generateCode(CodeAppender codeAppender, CallExpression callExpression, Object value) {
        List<?> list = convertToList(value);
        if (list.isEmpty()) {
            codeAppender.appendNewLine("assertThat(" + callExpression + ").isEmpty();");
        } else {
            codeAppender.appendNewLine("assertThat(" + callExpression + ")");
            codeAppender.sameIndent(() -> {
                codeAppender.appendNewLine(".hasSize(" + list.size() + ")");
                boolean isNotOrdered = value instanceof Set<?> && !(value instanceof SortedSet<?>);
                if (isAllItemsCanBeConverted(list)) {
                    addAssertForConvertedItems(codeAppender, list, isNotOrdered);
                } else {
                    String itemName = nameGenerator.generateItemName(callExpression.getNameOfLastCall());
                    addAssertForNotConvertableObjects(codeAppender, list, itemName, isNotOrdered);
                }
            });
        }
    }

    private List<?> convertToList(Object value) {
        if (value instanceof Iterable<?> iterable) {
            return StreamSupport.stream(iterable.spliterator(), false)
                    .toList();
        } else {
            return convertArrayToList(value);
        }
    }

    private List<?> convertArrayToList(Object value) {
        if (value instanceof Object[] array) {
            return Arrays.stream(array).toList();
        } else if (value instanceof byte[] array) {
            List<Byte> bytes = new ArrayList<>();
            for (byte b : array) {
                bytes.add(b);
            }
            return bytes;
        } else if (value instanceof short[] array) {
            List<Short> shorts = new ArrayList<>();
            for (short s : array) {
                shorts.add(s);
            }
            return shorts;
        } else if (value instanceof int[] array) {
            return Arrays.stream(array).boxed().toList();
        } else if (value instanceof long[] array) {
            return Arrays.stream(array).boxed().toList();
        } else if (value instanceof float[] array) {
            List<Float> floats = new ArrayList<>();
            for (float f : array) {
                floats.add(f);
            }
            return floats;
        } else if (value instanceof double[] array) {
            return Arrays.stream(array).boxed().toList();
        } else if (value instanceof boolean[] array) {
            List<Boolean> booleans = new ArrayList<>();
            for (boolean bool : array) {
                booleans.add(bool);
            }
            return booleans;
        } else if (value instanceof char[] array) {
            List<Character> characters = new ArrayList<>();
            for (char character : array) {
                characters.add(character);
            }
            return characters;
        }
        throw new RuntimeException();
    }

    private boolean isAllItemsCanBeConverted(List<?> list) {
        return list.stream()
                .allMatch(valueCodeConverterService::canConvert);
    }

    private void addAssertForConvertedItems(CodeAppender codeAppender, List<?> list, boolean isNotOrdered) {
        String codeItems = list.stream()
                .map(valueCodeConverterService::convertValueToCode)
                .map(Optional::orElseThrow)
                .collect(joining(", "));
        String assertJMethod = isNotOrdered ? "containsExactlyInAnyOrder" : "containsExactly";
        codeAppender.appendNewLine("." + assertJMethod + "(" + codeItems + ");");
    }

    private void addAssertForNotConvertableObjects(CodeAppender codeAppender, List<?> list, String itemName, boolean isNotOrdered) {
        String assertJMethod = isNotOrdered ? "satisfiesExactlyInAnyOrder" : "satisfiesExactly";
        codeAppender.appendNewLine("." + assertJMethod + "(");
        codeAppender.sameIndent(() -> {
            for (int i = 0; i < list.size(); i++) {
                codeAppender.appendNewLine(itemName + " -> {");
                int index = i;
                codeAppender.sameIndent(() -> {
                    CallExpression callExpression = CallExpression.ofReference(itemName);
                    Object item = list.get(index);
                    resultGeneratorProvider.findSuitable(item).generateCode(codeAppender, callExpression, item);
                });
                if (i != list.size() - 1) {
                    codeAppender.appendNewLine("},");
                } else {
                    codeAppender.appendNewLine("}");
                }
            }
        });
        codeAppender.appendNewLine(");");
    }
}
