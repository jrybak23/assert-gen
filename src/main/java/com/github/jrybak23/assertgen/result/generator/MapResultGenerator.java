package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.value.converter.ValueCodeConverterService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Setter
public class MapResultGenerator implements ResultGenerator {

    private final ValueCodeConverterService valueCodeConverterService;
    private ResultGeneratorProvider resultGeneratorProvider;

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof Map<?, ?>;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, String code, Object value) {
        var map = new LinkedHashMap<>((Map<?, ?>) value);
        if (map.isEmpty()) {
            codeAppender.appendNewLine("assertThat(" + code +").isEmpty();");
        } else {
            codeAppender.appendNewLine("assertThat(" + code +")");
            codeAppender.appendNewLine(".hasSize(" + map.size() + ")");
            var entries = new ArrayList<>(map.entrySet());
            for (int i = 0; i < entries.size(); i++) {
                Map.Entry<?, ?> entry = entries.get(i);
                int index = i;
                valueCodeConverterService.convertValueToCode(entry.getKey()).ifPresent(convertedKey -> {
                    codeAppender.appendNewLine(".hasEntrySatisfying(" + convertedKey + ", value -> {");
                    codeAppender.sameIndent(() -> {
                        ResultGenerator resultGenerator = resultGeneratorProvider.findSuitable(entry.getValue());
                        resultGenerator.generateCode(codeAppender, "value", entry.getValue());
                    });
                    if (index == entries.size() - 1) {
                        codeAppender.appendNewLine("});");
                    } else {
                        codeAppender.appendNewLine("})");
                    }
                });
            }

        }
    }
}
