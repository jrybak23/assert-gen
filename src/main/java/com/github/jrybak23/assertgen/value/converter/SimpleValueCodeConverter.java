package com.github.jrybak23.assertgen.value.converter;

import java.util.List;

public class SimpleValueCodeConverter implements ValueCodeConverter {

    @Override
    public List<Class<?>> getSuitableClass() {
        return List.of(Integer.class, Byte.class, Short.class, Boolean.class);
    }

    @Override
    public String convert(Object object) {
        return object.toString();
    }
}
