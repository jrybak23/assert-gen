package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.NameGenerator;
import com.github.jrybak23.assertgen.call.experession.CallExpression;
import com.github.jrybak23.assertgen.value.converter.ValueCodeConverterService;

import java.util.Spliterator;

class IterableResultGenerator extends SpliteratableResultGenerator{

    IterableResultGenerator(ValueCodeConverterService valueCodeConverterService, NameGenerator nameGenerator) {
        super(valueCodeConverterService, nameGenerator);
    }

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof Iterable<?>;
    }

    @Override
    Spliterator<?> getSpliterator(Object value) {
        return ((Iterable<?>)value).spliterator();
    }

    @Override
    void appendAssertThatIsEmpty(CodeAppender codeAppender, CallExpression callExpression) {
        codeAppender.appendNewLine("assertThat(" + callExpression + ").isEmpty();");
    }

    @Override
    void appendAssertThat(CodeAppender codeAppender, CallExpression callExpression) {
        codeAppender.appendNewLine("assertThat(" + callExpression + ")");
    }
}
