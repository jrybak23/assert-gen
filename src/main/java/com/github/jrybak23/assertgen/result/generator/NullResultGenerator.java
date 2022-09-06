package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.call.experession.CallExpression;

class NullResultGenerator implements ResultGenerator {

    @Override
    public boolean isSuitable(Object value) {
        return value == null;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, CallExpression callExpression, Object value) {
        codeAppender.appendNewLine("assertThat(" + callExpression + ").isNull();");
    }
}
