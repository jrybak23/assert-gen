package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.NameGenerator;
import com.github.jrybak23.assertgen.value.converter.ValueCodeConverterService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
@Setter
public class IterableResultGenerator implements ResultGenerator {

    private final ValueCodeConverterService valueCodeConverterService;
    private final NameGenerator nameGenerator;
    private ResultGeneratorProvider resultGeneratorProvider;

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof Iterable<?>;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, String code, Object value) {
        List<?> list = StreamSupport.stream(((Iterable<?>) value).spliterator(), false)
                .toList();
        if (list.isEmpty()) {
            codeAppender.appendNewLine("assertThat(" + code + ").isEmpty();");
        } else {
            codeAppender.appendNewLine("assertThat(" + code + ")");
            codeAppender.sameIndent(() -> {
                codeAppender.appendNewLine(".hasSize(" + list.size() + ")");
                boolean isNotOrdered = value instanceof Set<?> && !(value instanceof SortedSet<?>);
                if (isAllItemsCanBeConverted(list)) {
                    addAssertForConvertedItems(codeAppender, list, isNotOrdered);
                } else {
                    String itemName = nameGenerator.generateItemName(code);
                    addAssertForNotConvertableObjects(codeAppender, list, itemName);
                }
            });
        }
    }

    private boolean isAllItemsCanBeConverted(List<?> list) {
        return list.stream()
                .map(Object::getClass)
                .allMatch(valueCodeConverterService::canConvert);
    }

    private void addAssertForConvertedItems(CodeAppender codeAppender, List<?> list, boolean isNotOrdered) {
        String codeItems = list.stream()
                .map(valueCodeConverterService::convertValueToCode)
                .map(Optional::orElseThrow)
                .collect(joining(", "));
        String assertMethod =  isNotOrdered ? "containsExactlyInAnyOrder" : "containsExactly";
        codeAppender.appendNewLine("." + assertMethod + "(" + codeItems + ");");
    }

    private void addAssertForNotConvertableObjects(CodeAppender codeAppender, List<?> list, String itemName) {
        codeAppender.appendNewLine(".satisfiesExactly(");
        codeAppender.sameIndent(() -> {
            for (int i = 0; i < list.size(); i++) {
                codeAppender.appendNewLine(itemName + " -> {");
                int index = i;
                codeAppender.sameIndent(() -> {
                    Object item = list.get(index);
                    resultGeneratorProvider.findSuitable(item).generateCode(codeAppender, itemName, item);
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
