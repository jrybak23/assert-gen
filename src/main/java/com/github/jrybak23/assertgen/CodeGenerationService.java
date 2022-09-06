package com.github.jrybak23.assertgen;

import com.github.jrybak23.assertgen.call.experession.CallExpression;
import com.github.jrybak23.assertgen.result.generator.ResultGeneratorProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CodeGenerationService {

    private final static ResultGeneratorProvider resultGeneratorProvider = new ResultGeneratorProvider();

    public String generateCode(Object inputObject) {
        CodeAppender codeAppender = new CodeAppender();
        resultGeneratorProvider.findSuitable(inputObject)
                .generateCode(codeAppender, CallExpression.ofReference("result"), inputObject);
        return codeAppender.getResult();
    }
}
