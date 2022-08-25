package com.github.jrybak23.assertgen;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NameGeneratorTest {

    @Test
    void test() {
        NameGenerator nameGenerator = new NameGenerator();
        String result = nameGenerator.generateItemName("foo.object.getAnotherItems()");
        assertThat(result).isEqualTo("anotherItem");
    }
}