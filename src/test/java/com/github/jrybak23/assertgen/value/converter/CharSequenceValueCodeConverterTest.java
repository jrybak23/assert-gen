package com.github.jrybak23.assertgen.value.converter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CharSequenceValueCodeConverterTest {

    public static final CharSequenceValueCodeConverter charSequenceValueCodeConverter = new CharSequenceValueCodeConverter();

    @Test
    void testEscapeChars() {
        String result = charSequenceValueCodeConverter.convert("\\ \t \b \n \r \f \"");
        assertThat(result).isEqualTo("\"\\\\ \\t \\b \\n \\r \\f \\\"\"");
    }
}