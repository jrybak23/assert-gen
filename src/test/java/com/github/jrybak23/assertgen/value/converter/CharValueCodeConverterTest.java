package com.github.jrybak23.assertgen.value.converter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CharValueCodeConverterTest {

    public static final CharValueCodeConverter charValueCodeConverter = new CharValueCodeConverter();

    @Test
    void testEscapeChars() {
        String result = charValueCodeConverter.convert('\'');

        assertThat(result).isEqualTo("'\\''");
    }
}