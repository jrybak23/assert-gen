package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.call.experession.CallExpression;

public interface ResultGenerator {

    boolean isSuitable(Object value);

    void generateCode(CodeAppender codeAppender, CallExpression callExpression, Object value);
}
