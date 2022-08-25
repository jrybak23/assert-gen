package com.github.jrybak23.assertgen;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Builder
@Data
public class SomeClass {
    private final String a;
    private final long b;
    private final Double d;
    private final List<String> items;
    private final List<AnotherClass> anotherItems;
    private final Map<String, AnotherClass> map;
    private final CustomEnum customEnum;

}
