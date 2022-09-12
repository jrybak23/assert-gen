package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.NameGenerator;
import com.github.jrybak23.assertgen.call.experession.CallExpression;
import com.github.jrybak23.assertgen.value.converter.ValueCodeConverterService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;

@RequiredArgsConstructor
abstract class SpliteratableResultGenerator implements ResultGenerator {

    private static final int MAX_CONVERTED_VALUES_ROW_SIZE = 15;
    private final ValueCodeConverterService valueCodeConverterService;
    private final NameGenerator nameGenerator;
    private final ResultGeneratorService resultGeneratorService;

    @Override
    public void generateCode(CodeAppender codeAppender, CallExpression callExpression, Object value) {
        Spliterator<?> spliterator = getSpliterator(value);
        List<?> list = createList(spliterator);
        long size = list.size();
        if (size == 0) {
            appendAssertThatIsEmpty(codeAppender, callExpression);
        } else {
            appendAssertThat(codeAppender, callExpression);
            codeAppender.sameIndent(() -> {
                codeAppender.appendNewLine(".hasSize(" + size + ")");
                if (isAllItemsCanBeConverted(list)) {
                    addAssertForConvertedItems(codeAppender, list, isOrdered(spliterator));
                } else {
                    String itemName = nameGenerator.generateItemName(callExpression.getNameOfLastCall());
                    addAssertForNotConvertableObjects(codeAppender, list, itemName, isOrdered(spliterator));
                }
            });
        }
    }

    private static List<?> createList(Spliterator<?> spliterator) {
        if (isOrdered(spliterator)) {
            return StreamSupport.stream(spliterator, false)
                    .toList();
        } else {
            return StreamSupport.stream(spliterator, false)
                    .sorted(nullsLast(comparing(Object::hashCode)))
                    .toList();
        }
    }

    abstract Spliterator<?> getSpliterator(Object value);

    abstract void appendAssertThatIsEmpty(CodeAppender codeAppender, CallExpression callExpression);

    abstract void appendAssertThat(CodeAppender codeAppender, CallExpression callExpression);

    private static boolean isOrdered(Spliterator<?> spliterator) {
        return spliterator.hasCharacteristics(Spliterator.ORDERED);
    }

    private boolean isAllItemsCanBeConverted(List<?> list) {
        return list.stream()
                .allMatch(valueCodeConverterService::canConvert);
    }

    private void addAssertForConvertedItems(CodeAppender codeAppender, List<?> list, boolean isOrdered) {
        List<String> codeItems = list.stream()
                .map(valueCodeConverterService::convertValueToCode)
                .map(Optional::orElseThrow)
                .toList();
        String convertedValuesRow = String.join(", ", codeItems);
        String assertJMethod = isOrdered ? "containsExactly" : "containsExactlyInAnyOrder";
        if (convertedValuesRow.length() < MAX_CONVERTED_VALUES_ROW_SIZE) {
            codeAppender.appendNewLine("." + assertJMethod + "(" + convertedValuesRow + ");");
        } else {
            codeAppender.appendNewLine("." + assertJMethod + "(");
            codeAppender.sameIndent(() -> {
                for (int i = 0; i < codeItems.size(); i++) {
                    if (i < codeItems.size() - 1) {
                        codeAppender.appendNewLine(codeItems.get(i) + ",");
                    } else {
                        codeAppender.appendNewLine(codeItems.get(i));
                    }
                }
            });
            codeAppender.appendNewLine(");");
        }
    }

    private void addAssertForNotConvertableObjects(CodeAppender codeAppender, List<?> list, String itemName, boolean isOrdered) {
        String assertJMethod = isOrdered ? "satisfiesExactly" : "satisfiesExactlyInAnyOrder";
        codeAppender.appendNewLine("." + assertJMethod + "(");
        codeAppender.sameIndent(() -> {
            for (int i = 0; i < list.size(); i++) {
                codeAppender.appendNewLine(itemName + " -> {");
                int index = i;
                codeAppender.sameIndent(() -> {
                    CallExpression callExpression = CallExpression.ofReference(itemName);
                    Object item = list.get(index);
                    resultGeneratorService.generate(codeAppender, callExpression, item);
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
