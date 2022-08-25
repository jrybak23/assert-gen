package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;

public class FloatingPointNumberResultGenerator implements ResultGenerator {

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof Float || value instanceof Double;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, String code, Object value) {
        char literal = value.getClass().getSimpleName().charAt(0);
        codeAppender.appendNewLine("assertThat(" + code + ").isEqualTo(" + value + literal + ", withPrecision(0.01" + literal + "));");
    }
}
