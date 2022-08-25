package com.github.jrybak23.assertgen;

import com.github.jrybak23.assertgen.result.generator.MapResultGenerator;
import com.github.jrybak23.assertgen.result.generator.ValueCodeConverterResultGenerator;
import com.github.jrybak23.assertgen.result.generator.FloatingPointNumberResultGenerator;
import com.github.jrybak23.assertgen.result.generator.IterableResultGenerator;
import com.github.jrybak23.assertgen.result.generator.NullResultGenerator;
import com.github.jrybak23.assertgen.result.generator.ObjectResultGenerator;
import com.github.jrybak23.assertgen.result.generator.ResultGenerator;
import com.github.jrybak23.assertgen.result.generator.ResultGeneratorProvider;
import com.github.jrybak23.assertgen.value.converter.ValueCodeConverterService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CodeGenerationService {

    private final static ResultGeneratorProvider resultGeneratorProvider = createProvider();

    private static ResultGeneratorProvider createProvider() {
        ObjectResultGenerator objectResultGenerator = new ObjectResultGenerator();
        ValueCodeConverterService valueCodeConverterService = new ValueCodeConverterService();
        IterableResultGenerator iterableResultGenerator = new IterableResultGenerator(valueCodeConverterService, new NameGenerator());
        MapResultGenerator mapResultGenerator = new MapResultGenerator(valueCodeConverterService);
        List<ResultGenerator> resultGenerators = List.of(
                new NullResultGenerator(),
                new FloatingPointNumberResultGenerator(),
                new ValueCodeConverterResultGenerator(valueCodeConverterService),
                mapResultGenerator,
                iterableResultGenerator,
                objectResultGenerator
        );
        ResultGeneratorProvider provider = new ResultGeneratorProvider(resultGenerators);
        objectResultGenerator.setResultGeneratorProvider(provider);
        mapResultGenerator.setResultGeneratorProvider(provider);
        iterableResultGenerator.setResultGeneratorProvider(provider);
        return provider;
    }

    public String generateCode(Object inputObject) {
        CodeAppender codeAppender = new CodeAppender();
        ResultGenerator resultGenerator = resultGeneratorProvider.findSuitable(inputObject);
        resultGenerator.generateCode(codeAppender, "object", inputObject);
        return codeAppender.getResult();
    }
}
