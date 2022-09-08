package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.AccessorsProvider;
import com.github.jrybak23.assertgen.NameGenerator;
import com.github.jrybak23.assertgen.value.converter.ValueCodeConverterService;

import java.util.List;

public class ResultGeneratorProvider {

    public ResultGeneratorProvider() {
        ObjectResultGenerator objectResultGenerator = new ObjectResultGenerator(new AccessorsProvider());
        ValueCodeConverterService valueCodeConverterService = new ValueCodeConverterService();
        NameGenerator nameGenerator = new NameGenerator();
        IterableAndArrayResultGenerator iterableAndArrayResultGenerator = new IterableAndArrayResultGenerator(valueCodeConverterService, nameGenerator);
        MapResultGenerator mapResultGenerator = new MapResultGenerator(valueCodeConverterService, nameGenerator);
        OptionalResultGenerator optionalResultGenerator = new OptionalResultGenerator();
        OptionalPrimitiveResultGenerator optionalPrimitiveResultGenerator = new OptionalPrimitiveResultGenerator();
        List<ResultGenerator> resultGenerators = List.of(
                new NullResultGenerator(),
                new SkipValueResultGenerator(),
                new MultiLineCharSequenceResultGenerator(),
                new FloatingPointNumberResultGenerator(),
                new BooleanResultGenerator(),
                optionalResultGenerator,
                optionalPrimitiveResultGenerator,
                new ValueCodeConverterResultGenerator(valueCodeConverterService),
                mapResultGenerator,
                iterableAndArrayResultGenerator,
                objectResultGenerator
        );
        objectResultGenerator.setResultGeneratorProvider(this);
        mapResultGenerator.setResultGeneratorProvider(this);
        iterableAndArrayResultGenerator.setResultGeneratorProvider(this);
        optionalResultGenerator.setResultGeneratorProvider(this);
        optionalPrimitiveResultGenerator.setResultGeneratorProvider(this);
        this.resultGenerators = resultGenerators;
    }

    private final List<ResultGenerator> resultGenerators;

    public ResultGenerator findSuitable(Object object) {
        return resultGenerators.stream()
                .filter(generator -> generator.isSuitable(object))
                .findFirst()
                .orElseThrow();
    }

}
