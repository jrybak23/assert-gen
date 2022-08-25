package com.github.jrybak23.assertgen.value.converter;

import java.util.List;

public interface ValueCodeConverter {

    List<Class<?>> getSuitableClass();

    String convert(Object object);

}
