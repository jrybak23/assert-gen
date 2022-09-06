package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.StringUtils;
import com.github.jrybak23.assertgen.call.experession.CallExpression;

import java.util.Arrays;

import static com.github.jrybak23.assertgen.StringUtils.isMultiline;

class MultiLineCharSequenceResultGenerator implements ResultGenerator {

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof CharSequence sequence && isMultiline(sequence.toString());
    }

    @Override
    public void generateCode(CodeAppender codeAppender, CallExpression callExpression, Object value) {
        codeAppender.appendNewLine("assertThat(" + callExpression + ").isEqualTo(\"\"\"");
        codeAppender.sameIndent(() -> {
            Arrays.stream(((CharSequence) value).toString().split("\n"))
                    .map(StringUtils::escapeChars)
                    .forEach(codeAppender::appendNewLine);
            codeAppender.appendNewLine("\"\"\");");
        });
    }
}
