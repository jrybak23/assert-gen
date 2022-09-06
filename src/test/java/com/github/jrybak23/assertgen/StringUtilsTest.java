package com.github.jrybak23.assertgen;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @Test
    void testSingleLine() {
        boolean result = StringUtils.isMultiline("a\n");
        assertThat(result).isFalse();
    }

    @Test
    void testMultiLine() {
        boolean result = StringUtils.isMultiline("""
                a
                b
                """);
        assertThat(result).isTrue();
    }
}