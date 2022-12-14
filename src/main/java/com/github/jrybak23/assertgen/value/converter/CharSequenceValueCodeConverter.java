package com.github.jrybak23.assertgen.value.converter;

import com.github.jrybak23.assertgen.StringUtils;

class CharSequenceValueCodeConverter implements ValueCodeConverter {

    @Override
    public boolean isSuitableFor(Object value) {
        return value instanceof CharSequence;
    }

    @Override
    public String convert(Object value) {
        value = StringUtils.escapeChars(value.toString());
        return "\"" + value + "\"";
    }
}
