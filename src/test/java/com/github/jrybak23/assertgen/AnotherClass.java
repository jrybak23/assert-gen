package com.github.jrybak23.assertgen;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class AnotherClass {
    private final String v1;
    private final Integer v2;
}
