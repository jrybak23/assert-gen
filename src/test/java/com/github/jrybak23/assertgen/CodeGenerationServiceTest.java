package com.github.jrybak23.assertgen;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
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

    @Test
    void testChar() {
        String result = codeGenerationService.generateCode('c');

        assertThat(result).isEqualTo("assertThat(result).isEqualTo('c');\n");
    }

    @Test
    void testEmoji() {
        String result = codeGenerationService.generateCode("🙂");

        assertThat(result).isEqualTo("assertThat(result).isEqualTo(\"🙂\");\n");
    }

    @Test
    void testNullElementCollection() {
        List<String> list = new ArrayList<>();
        list.add(null);
        list.add("someStr");
        list.add(null);

        String result = codeGenerationService.generateCode(list);

        assertThat(result).isEqualTo("""
                assertThat(result)
                        .hasSize(3)
                        .containsExactly(null, "someStr", null);
                """);
    }
}