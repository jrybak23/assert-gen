package com.github.jrybak23.assertgen;

import com.github.jrybak23.assertgen.call.experession.CallExpression;
import com.github.jrybak23.assertgen.result.generator.BooleanResultGenerator;
import com.github.jrybak23.assertgen.result.generator.MapResultGenerator;
import com.github.jrybak23.assertgen.result.generator.OptionalResultGenerator;
import com.github.jrybak23.assertgen.result.generator.ValueCodeConverterResultGenerator;
import com.github.jrybak23.assertgen.result.generator.FloatingPointNumberResultGenerator;
import com.github.jrybak23.assertgen.result.generator.IterableAndArrayResultGenerator;
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
        ObjectResultGenerator objectResultGenerator = new ObjectResultGenerator(new AccessorsProvider());
        ValueCodeConverterService valueCodeConverterService = new ValueCodeConverterService();
        IterableAndArrayResultGenerator iterableAndArrayResultGenerator = new IterableAndArrayResultGenerator(valueCodeConverterService, new NameGenerator());
        MapResultGenerator mapResultGenerator = new MapResultGenerator(valueCodeConverterService);
        OptionalResultGenerator optionalResultGenerator = new OptionalResultGenerator();
        List<ResultGenerator> resultGenerators = List.of(
                new NullResultGenerator(),
                new FloatingPointNumberResultGenerator(),
                new BooleanResultGenerator(),
                optionalResultGenerator,
                new ValueCodeConverterResultGenerator(valueCodeConverterService),
                mapResultGenerator,
                iterableAndArrayResultGenerator,
                objectResultGenerator
        );
        ResultGeneratorProvider provider = new ResultGeneratorProvider(resultGenerators);
        objectResultGenerator.setResultGeneratorProvider(provider);
        mapResultGenerator.setResultGeneratorProvider(provider);
        iterableAndArrayResultGenerator.setResultGeneratorProvider(provider);
        optionalResultGenerator.setResultGeneratorProvider(provider);
        return provider;
    }

    public String generateCode(Object inputObject) {
        CodeAppender codeAppender = new CodeAppender();
        resultGeneratorProvider.findSuitable(inputObject)
                .generateCode(codeAppender, CallExpression.ofReference("result"), inputObject);
        return codeAppender.getResult();
    }
}
