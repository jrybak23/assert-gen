package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.call.experession.CallExpression;

class FloatingPointNumberResultGenerator implements ResultGenerator {

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof Float || value instanceof Double;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, CallExpression callExpression, Object value) {
        char literal = value.getClass().getSimpleName().charAt(0);
        codeAppender.appendNewLine("assertThat(" + callExpression + ").isEqualTo(" + value + literal + ", withPrecision(0.01" + literal + "));");
    }
}
