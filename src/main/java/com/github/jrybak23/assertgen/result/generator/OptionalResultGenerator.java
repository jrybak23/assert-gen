package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.call.experession.CallExpression;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.Optional;

@RequiredArgsConstructor
class OptionalResultGenerator implements ResultGenerator {

    private final ResultGeneratorService resultGeneratorService;

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof Optional<?>;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, CallExpression callExpression, Object value) {
        var optional = (Optional<?>) value;
        if (optional.isEmpty()) {
            codeAppender.appendNewLine("assertThat(" + callExpression + ").isNotPresent();");
        } else {
            Object object = optional.orElseThrow();
            resultGeneratorService.generate(codeAppender, callExpression.addMethodCall(getOrElseThrowMethod()), object);
        }
    }

    private static Method getOrElseThrowMethod() {
        try {
            return Optional.class.getMethod("orElseThrow");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
