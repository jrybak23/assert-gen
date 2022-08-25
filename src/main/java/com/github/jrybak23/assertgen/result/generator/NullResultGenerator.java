package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;

public class NullResultGenerator implements ResultGenerator {

    @Override
    public boolean isSuitable(Object value) {
        return value == null;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, String code, Object value) {
        codeAppender.appendNewLine("assertThat(" + code + ").isNull();");
    }
}
