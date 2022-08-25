package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.value.converter.ValueCodeConverterService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValueCodeConverterResultGenerator implements ResultGenerator {

    private final ValueCodeConverterService valueCodeConverterService;

    @Override
    public boolean isSuitable(Object value) {
        return valueCodeConverterService.canConvert(value.getClass());
    }

    @Override
    public void generateCode(CodeAppender codeAppender, String code, Object value) {
        String convertedValue = valueCodeConverterService.convertValueToCode(value).orElseThrow();
        codeAppender.appendNewLine("assertThat(" + code + ").isEqualTo(" + convertedValue + ");");
    }
}
