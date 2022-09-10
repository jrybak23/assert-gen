package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.AccessorsProvider;
import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.NameGenerator;
import com.github.jrybak23.assertgen.call.experession.CallExpression;
import com.github.jrybak23.assertgen.value.converter.ValueCodeConverterService;

import java.util.List;

public class ResultGeneratorService {

    public ResultGeneratorService() {
        ValueCodeConverterService valueCodeConverterService = new ValueCodeConverterService();
        NameGenerator nameGenerator = new NameGenerator();
        this.resultGenerators = List.of(
                new NullResultGenerator(),
                new SkipValueResultGenerator(),
                new MultiLineCharSequenceResultGenerator(),
                new FloatingPointNumberResultGenerator(),
                new BooleanResultGenerator(),
                new OptionalResultGenerator(this),
                new OptionalPrimitiveResultGenerator(this),
                new ValueCodeConverterResultGenerator(valueCodeConverterService),
                new MapResultGenerator(valueCodeConverterService, nameGenerator, this),
                new IterableResultGenerator(valueCodeConverterService, nameGenerator, this),
                new ArrayResultGenerator(valueCodeConverterService, nameGenerator, this),
                new StreamResultGenerator(valueCodeConverterService, nameGenerator, this),
                new IteratorResultGenerator(valueCodeConverterService, nameGenerator, this),
                new SpliteratorResultGenerator(valueCodeConverterService, nameGenerator, this),
                new ObjectResultGenerator(new AccessorsProvider(), this)
        );
    }

    private final List<ResultGenerator> resultGenerators;

    public void generate(CodeAppender codeAppender, CallExpression callExpression, Object object) {
        findSuitable(object)
                .generateCode(codeAppender, callExpression, object);
    }

    private ResultGenerator findSuitable(Object object) {
        return resultGenerators.stream()
                .filter(generator -> generator.isSuitable(object))
                .findFirst()
                .orElseThrow();
    }

}
