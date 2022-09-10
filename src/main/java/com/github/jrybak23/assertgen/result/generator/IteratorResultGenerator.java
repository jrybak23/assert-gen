package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.NameGenerator;
import com.github.jrybak23.assertgen.call.experession.CallExpression;
import com.github.jrybak23.assertgen.value.converter.ValueCodeConverterService;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

class IteratorResultGenerator extends SpliteratableResultGenerator {

    IteratorResultGenerator(ValueCodeConverterService valueCodeConverterService, NameGenerator nameGenerator) {
        super(valueCodeConverterService, nameGenerator);
    }

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof Iterator<?>;
    }

    @Override
    Spliterator<?> getSpliterator(Object value) {
        return Spliterators.spliteratorUnknownSize((Iterator<?>) value, 0);
    }

    @Override
    void appendAssertThatIsEmpty(CodeAppender codeAppender, CallExpression callExpression) {
        codeAppender.appendNewLine("assertThat(" + callExpression + ").isExhausted();");
    }

    @Override
    protected void appendAssertThat(CodeAppender codeAppender, CallExpression callExpression) {
        codeAppender.appendNewLine("assertThat(" + callExpression + ").asList()");
    }
}
