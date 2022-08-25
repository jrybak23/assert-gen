package com.github.jrybak23.assertgen;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CodeGenerationServiceTest {

    @Test
    void name() {
        SomeClass object = SomeClass.builder()
                .a("value")
                .b(101L)
                .d(3D)
                .items(List.of("s1", "s2"))
                .customEnum(CustomEnum.FIRST)
                .anotherItems(
                        List.of(
                                AnotherClass.builder()
                                        .v1("v1")
                                        .v2(201)
                                        .build(),
                                AnotherClass.builder()
                                        .v1("v2")
                                        .v2(202)
                                        .build()
                        )
                )
                .map(Map.of("v3", AnotherClass.builder()
                        .v1("v2")
                        .v2(202)
                        .build()))
                .build();
        AssertGen.generate(object);

       assertThat(object.getMap())
               .hasEntrySatisfying("", value -> {

               });

   /*     CodeGenerationService service = new CodeGenerationService();
        String result = service.generateCode(LocalDate.now().getChronology().dateNow());
        System.out.println(result);*/

        assertThat(object.getAnotherItems())
                .hasSize(2)
                    .satisfiesExactly(
                        anotherItem -> {
                        },
                        anotherItem -> {
                        });

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