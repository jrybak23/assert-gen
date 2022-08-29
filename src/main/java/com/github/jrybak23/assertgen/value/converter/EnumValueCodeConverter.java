package com.github.jrybak23.assertgen.value.converter;

class EnumValueCodeConverter implements ValueCodeConverter {

    @Override
    public boolean isSuitableFor(Object value) {
        return value instanceof Enum<?>;
    }

    @Override
    public String convert(Object value) {
        return value.getClass().getSimpleName() + "." + value;
    }
}
