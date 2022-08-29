package com.github.jrybak23.assertgen.value.converter;

interface ValueCodeConverter {

    boolean isSuitableFor(Object value);

    String convert(Object value);

}
