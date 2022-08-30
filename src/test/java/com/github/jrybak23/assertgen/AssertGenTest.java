package com.github.jrybak23.assertgen;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

class AssertGenTest {

    @Test
    void test() throws InterruptedException {
        AssertGen.generate(SomeClassProvider.object);
    }
}