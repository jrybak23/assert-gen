package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;

public class BooleanResultGenerator implements ResultGenerator {

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof Boolean;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, String code, Object value) {
        if ((Boolean) value) {
            codeAppender.appendNewLine("assertThat(" + code + ").isTrue();");
        } else {
            codeAppender.appendNewLine("assertThat(" + code + ").isFalse();");
        }
    }
}
