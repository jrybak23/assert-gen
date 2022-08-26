package com.github.jrybak23.assertgen;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CodeGenerationServiceTest {

    @Test
    void name() {
        var object = SomeClassProvider.object;
        AssertGen.generate(object);

       /* assertThat(result.getB()).isEqualTo(101L);
        assertThat(result.getD()).isEqualTo(3.0D, withPrecision(0.01D));
        assertThat(result.getA()).isEqualTo("value");
        assertThat(result.getItems())
                .hasSize(2)
                .containsExactly("s1","s2")
        assertThat(result.getAnotherItems())
                .hasSize(2)
                .satisfiesExactly(
                        item -> {
                        },
                        item -> {
                        }
                )*/

      /*  assertThat(object.getB()).isNull();
        assertThat(object.getA()).isEqualTo("value");
        assertThat(object.getD()).isEqualTo(3D);*/

/*
        Iterable<String> iterable = List.of("sa");


        assertThat(iterable)
                .containsExactly("sa");*/
    }
}