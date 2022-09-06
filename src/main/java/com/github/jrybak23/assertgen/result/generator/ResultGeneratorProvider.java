package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.AccessorsProvider;
import com.github.jrybak23.assertgen.NameGenerator;
import com.github.jrybak23.assertgen.value.converter.ValueCodeConverterService;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class ResultGeneratorProvider {

    public ResultGeneratorProvider() {
        ObjectResultGenerator objectResultGenerator = new ObjectResultGenerator(new AccessorsProvider());
        ValueCodeConverterService valueCodeConverterService = new ValueCodeConverterService();
        IterableAndArrayResultGenerator iterableAndArrayResultGenerator = new IterableAndArrayResultGenerator(valueCodeConverterService, new NameGenerator());
        MapResultGenerator mapResultGenerator = new MapResultGenerator(valueCodeConverterService);
        OptionalResultGenerator optionalResultGenerator = new OptionalResultGenerator();
        List<ResultGenerator> resultGenerators = List.of(
                new NullResultGenerator(),
                new SkipValueResultGenerator(),
                new FloatingPointNumberResultGenerator(),
                new BooleanResultGenerator(),
                optionalResultGenerator,
                new ValueCodeConverterResultGenerator(valueCodeConverterService),
                mapResultGenerator,
                iterableAndArrayResultGenerator,
                objectResultGenerator
        );
        objectResultGenerator.setResultGeneratorProvider(this);
        mapResultGenerator.setResultGeneratorProvider(this);
        iterableAndArrayResultGenerator.setResultGeneratorProvider(this);
        optionalResultGenerator.setResultGeneratorProvider(this);
        this.resultGenerators = resultGenerators;
    }

    private List<ResultGenerator> resultGenerators;

    public ResultGenerator findSuitable(Object object) {
        return resultGenerators.stream()
                .filter(generator -> generator.isSuitable(object))
                .findFirst()
                .orElseThrow();
    }

}
