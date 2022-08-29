package com.github.jrybak23.assertgen.value.converter;

class LiteralNumberValueCodeConverter implements ValueCodeConverter {

    @Override
    public boolean isSuitableFor(Object value) {
        return value instanceof Float
                || value instanceof Double
                || value instanceof Long;
    }

    @Override
    public String convert(Object value) {
        char literal = value.getClass().getSimpleName().charAt(0);
        return value + String.valueOf(literal);
    }
}
