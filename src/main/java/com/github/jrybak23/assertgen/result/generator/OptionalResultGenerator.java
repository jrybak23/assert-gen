package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import lombok.Setter;

import java.util.Optional;

@Setter
public class OptionalResultGenerator implements ResultGenerator {

    private ResultGeneratorProvider resultGeneratorProvider;

    @Override
    public boolean isSuitable(Object value) {
        return value instanceof Optional<?>;
    }

    @Override
    public void generateCode(CodeAppender codeAppender, String code, Object value) {
        var optional = (Optional<?>) value;
        if (optional.isEmpty()) {
            codeAppender.appendNewLine("assertThat(" + code + ").isNotPresent();");
        } else {
            Object object = optional.orElseThrow();
            resultGeneratorProvider.findSuitable(object)
                    .generateCode(codeAppender, code + ".orElseThrow()", object);
        }
    }
}
