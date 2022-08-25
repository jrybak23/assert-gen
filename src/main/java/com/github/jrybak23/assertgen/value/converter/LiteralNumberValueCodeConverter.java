package com.github.jrybak23.assertgen.value.converter;

import java.util.List;

public class LiteralNumberValueCodeConverter implements ValueCodeConverter {

    @Override
    public List<Class<?>> getSuitableClass() {
        return List.of(Float.class, Double.class, Long.class);
    }

    @Override
    public String convert(Object object) {
        char literal = object.getClass().getSimpleName().charAt(0);
        return object + String.valueOf(literal);
    }
}
