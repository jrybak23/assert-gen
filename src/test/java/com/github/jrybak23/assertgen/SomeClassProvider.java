package com.github.jrybak23.assertgen;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SomeClassProvider {
    public static SomeClass object = SomeClass.builder()
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
            .dateTime(LocalDateTime.now())
            .intArray(new int[]{4, 5})
            .someClass(SomeClass.builder().d(32D).build())
            .build();
}
