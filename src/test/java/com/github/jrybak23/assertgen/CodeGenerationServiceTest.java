package com.github.jrybak23.assertgen;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CodeGenerationServiceTest {

    private static final CodeGenerationService codeGenerationService = new CodeGenerationService();

    @Test
    void testTrueBoolean() {
        String result = codeGenerationService.generateCode(true);

        assertThat(result).isEqualTo("assertThat(result).isTrue();\n");
    }

    @Test
    void testFalseBoolean() {
        String result = codeGenerationService.generateCode(false);

        assertThat(result).isEqualTo("assertThat(result).isFalse();\n");
    }
}