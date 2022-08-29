package com.github.jrybak23.assertgen.value.converter;

class NullValueCodeConverter implements ValueCodeConverter {

    @Override
    public boolean isSuitableFor(Object value) {
        return value == null;
    }

    @Override
    public String convert(Object value) {
        return "null";
    }
}
