package com.github.jrybak23.assertgen.value.converter;
class StringValueCodeConverter implements ValueCodeConverter {

    @Override
    public boolean isSuitableFor(Object value) {
        return value instanceof String;
    }

    @Override
    public String convert(Object value) {
        return "\"" + value + "\"";
    }
}
