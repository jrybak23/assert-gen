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
        SpliteratableResultGenerator iterableResultGenerator = new IterableResultGenerator(valueCodeConverterService, nameGenerator);
        SpliteratableResultGenerator arrayResultGenerator = new ArrayResultGenerator(valueCodeConverterService, nameGenerator);
        SpliteratableResultGenerator streamResultGenerator = new StreamResultGenerator(valueCodeConverterService, nameGenerator);
        SpliteratableResultGenerator iteratorResultGenerator = new IteratorResultGenerator(valueCodeConverterService, nameGenerator);
        SpliteratableResultGenerator spliteratorResultGenerator = new SpliteratorResultGenerator(valueCodeConverterService, nameGenerator);
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
                iterableResultGenerator,
                arrayResultGenerator,
                streamResultGenerator,
                iteratorResultGenerator,
                spliteratorResultGenerator,
                objectResultGenerator
        );
        objectResultGenerator.setResultGeneratorProvider(this);
        mapResultGenerator.setResultGeneratorProvider(this);
        iterableResultGenerator.setResultGeneratorProvider(this);
        arrayResultGenerator.setResultGeneratorProvider(this);
        streamResultGenerator.setResultGeneratorProvider(this);
        iteratorResultGenerator.setResultGeneratorProvider(this);
        spliteratorResultGenerator.setResultGeneratorProvider(this);
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
