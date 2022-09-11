package com.github.jrybak23.assertgen.result.generator;

import java.util.List;

class SpringResponseEntityResultGenerator extends SpecificMethodInvocationResultGenerator {

    private static final List<String> METHODS_TO_INVOKE = List.of("getStatusCode", "getBody", "getHeaders");

    SpringResponseEntityResultGenerator(ResultGeneratorService resultGeneratorService) {
        super(resultGeneratorService);
    }

    @Override
    public boolean isSuitable(Object value) {
        return value.getClass().getName().equals("org.springframework.http.ResponseEntity");
    }

    @Override
    List<String> getMethodsToInvoke() {
        return METHODS_TO_INVOKE;
    }
}
