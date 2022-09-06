package com.github.jrybak23.assertgen;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

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

    @Test
    void testFloatArray() {
        float[] array = {3F, 4.5F};

        String result = codeGenerationService.generateCode(array);

        assertThat(result).isEqualTo("""
                assertThat(result)
                        .hasSize(2)
                        .containsExactly(3.0F, 4.5F);
                """);
    }

    @Test
    void testByteArray() {
        byte[] array = {2, 7};

        String result = codeGenerationService.generateCode(array);

        assertThat(result).isEqualTo("""
                assertThat(result)
                        .hasSize(2)
                        .containsExactly(2, 7);
                """);
    }

    @Test
    void testNotEmptyOptional() {
        Optional<String> optional = Optional.of("some str");

        String result = codeGenerationService.generateCode(optional);

        assertThat(result).isEqualTo("assertThat(result.orElseThrow()).isEqualTo(\"some str\");\n");
    }

    @Test
    void testEmptyOptional() {
        Optional<String> optional = Optional.empty();

        String result = codeGenerationService.generateCode(optional);

        assertThat(result).isEqualTo("assertThat(result).isNotPresent();\n");
    }

    @Test
    void testOptionalLong() {
        OptionalLong optional = OptionalLong.of(3L);

        String result = codeGenerationService.generateCode(optional);

        assertThat(result).isEqualTo("assertThat(result.orElseThrow()).isEqualTo(3L);\n");
    }

    @Test
    void testEmptyOptionalLong() {
        OptionalLong optional = OptionalLong.empty();

        String result = codeGenerationService.generateCode(optional);

        assertThat(result).isEqualTo("assertThat(result).isEmpty();\n");
    }

    @Test
    void testMultiline() {
        String str = """
                a
                b
                c
                """;

        String result = codeGenerationService.generateCode(str);

        assertThat(result).isEqualTo("""
                assertThat(result).isEqualTo(\"\"\"
                        a
                        b
                        c
                        \"\"\");
                """);
    }
}