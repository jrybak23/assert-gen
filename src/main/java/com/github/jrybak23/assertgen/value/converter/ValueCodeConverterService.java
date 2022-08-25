package com.github.jrybak23.assertgen.value.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ValueCodeConverterService {

    private static final HashMap<Class<?>, ValueCodeConverter> CODE_CONVERTERS_MAP = createCodeConvertersMap();

    private static HashMap<Class<?>, ValueCodeConverter> createCodeConvertersMap() {
        List<ValueCodeConverter> valueCodeConverters = List.of(
                new StringValueCodeConverter(),
                new LiteralNumberValueCodeConverter(),
                new SimpleValueCodeConverter()
        );
        HashMap<Class<?>, ValueCodeConverter> converters = new HashMap<>();
        for (ValueCodeConverter valueCodeConverter : valueCodeConverters) {
            for (Class<?> classInstance : valueCodeConverter.getSuitableClass()) {
                converters.put(classInstance, valueCodeConverter);
            }
        }
        return converters;
    }

    public boolean canConvert(Class<?> aClass) {
        return CODE_CONVERTERS_MAP.containsKey(aClass);
    }

    public Optional<String> convertValueToCode(Object value) {
        return Optional.ofNullable(CODE_CONVERTERS_MAP.get(value.getClass()))
                .map(valueCodeConverter -> valueCodeConverter.convert(value));
    }
}
