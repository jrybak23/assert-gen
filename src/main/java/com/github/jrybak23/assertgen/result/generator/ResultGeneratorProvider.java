package com.github.jrybak23.assertgen.result.generator;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ResultGeneratorProvider {

    private final List<ResultGenerator> resultGenerators;

    public ResultGenerator findSuitable(Object object) {
        return resultGenerators.stream()
                .filter(generator -> generator.isSuitable(object))
                .findFirst()
                .orElseThrow();
    }

}
