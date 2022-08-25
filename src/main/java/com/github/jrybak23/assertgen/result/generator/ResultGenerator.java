package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;

public interface ResultGenerator {

    boolean isSuitable(Object value);

    void generateCode(CodeAppender codeAppender, String code, Object value);
}
