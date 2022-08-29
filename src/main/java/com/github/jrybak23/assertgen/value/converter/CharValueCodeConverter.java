package com.github.jrybak23.assertgen.value.converter;

import com.github.jrybak23.assertgen.StringUtils;

class CharValueCodeConverter implements ValueCodeConverter {

    @Override
    public boolean isSuitableFor(Object value) {
        return value instanceof Character;
    }

    @Override
    public String convert(Object value) {
        String escapedChar = StringUtils.escapeChars(String.valueOf(value));
        return "'" + escapedChar + "'";
    }
}
