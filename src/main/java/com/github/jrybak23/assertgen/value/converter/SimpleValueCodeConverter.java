package com.github.jrybak23.assertgen.value.converter;

class SimpleValueCodeConverter implements ValueCodeConverter {

    @Override
    public boolean isSuitableFor(Object value) {
        return value instanceof Integer
                || value instanceof Byte
                || value instanceof Short
                || value instanceof Boolean;
    }

    @Override
    public String convert(Object value) {
        return value.toString();
    }
}
