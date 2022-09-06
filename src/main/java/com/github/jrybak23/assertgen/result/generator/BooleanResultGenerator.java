package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.call.experession.CallExpression;

class BooleanResultGenerator implements ResultGenerator {

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof Boolean;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, CallExpression callExpression, Object value) {
        if ((Boolean) value) {
            codeAppender.appendNewLine("assertThat(" + callExpression + ").isTrue();");
        } else {
            codeAppender.appendNewLine("assertThat(" + callExpression + ").isFalse();");
        }
    }
}
