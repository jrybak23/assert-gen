package com.github.jrybak23.assertgen.value.converter;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ParsableTemporalValueCodeConverterTest {

    @Test
    void testParseLocalDateTime() {
        var converter = new ParsableTemporalValueCodeConverter();

        LocalDateTime localDate = LocalDateTime.parse("2022-08-29T12:45:17.827462");
        boolean suitable = converter.isSuitableFor(localDate);
        String result = converter.convert(localDate);

        assertThat(suitable).isTrue();
        assertThat(result).isEqualTo("LocalDateTime.parse(\"2022-08-29T12:45:17.827462\")");
    }
}