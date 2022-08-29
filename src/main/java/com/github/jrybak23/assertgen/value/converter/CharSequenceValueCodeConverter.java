package com.github.jrybak23.assertgen.value.converter;

class CharSequenceValueCodeConverter implements ValueCodeConverter {

    @Override
    public boolean isSuitableFor(Object value) {
        return value instanceof CharSequence;
    }

    @Override
    public String convert(Object value) {
        value = value.toString()
                .replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("'", "\\'")
                .replace("\"", "\\\"");
        return "\"" + value + "\"";
    }
}
