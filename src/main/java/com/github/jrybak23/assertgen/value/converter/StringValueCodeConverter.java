package com.github.jrybak23.assertgen.value.converter;

import java.util.List;

public class StringValueCodeConverter implements ValueCodeConverter {

    @Override
    public List<Class<?>> getSuitableClass() {
        return List.of(String.class);
    }

    @Override
    public String convert(Object object) {
        return "\"" + object + "\"";
    }
}
