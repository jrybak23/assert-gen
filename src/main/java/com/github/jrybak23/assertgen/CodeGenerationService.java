package com.github.jrybak23.assertgen;

import com.github.jrybak23.assertgen.call.experession.CallExpression;
import com.github.jrybak23.assertgen.result.generator.ResultGeneratorService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CodeGenerationService {

    private final static ResultGeneratorService resultGeneratorService = new ResultGeneratorService();
    private final String reference;

    public String generateCode(Object inputObject) {
        CodeAppender codeAppender = new CodeAppender();
        resultGeneratorService.generate(codeAppender, CallExpression.ofReference(reference), inputObject);
        return codeAppender.getResult();
    }
}
