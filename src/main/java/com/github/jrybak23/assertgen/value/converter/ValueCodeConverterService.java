package com.github.jrybak23.assertgen.value.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ValueCodeConverterService {

    private static final Map<Class<?>, ValueCodeConverter> CODE_CONVERTERS_MAP = createCodeConvertersMap();

    private static Map<Class<?>, ValueCodeConverter> createCodeConvertersMap() {
        List<ValueCodeConverter> valueCodeConverters = List.of(
                new StringValueCodeConverter(),
                new LiteralNumberValueCodeConverter(),
                new SimpleValueCodeConverter()
        );
        Map<Class<?>, ValueCodeConverter> converters = new HashMap<>();
        for (ValueCodeConverter valueCodeConverter : valueCodeConverters) {
            for (Class<?> classInstance : valueCodeConverter.getSuitableClass()) {
                converters.put(classInstance, valueCodeConverter);
            }
        }
        return converters;
    }

    public boolean canConvert(Object object) {
        return CODE_CONVERTERS_MAP.containsKey(object.getClass()) || object instanceof Enum<?>;
    }

    public Optional<String> convertValueToCode(Object value) {
        if (value instanceof Enum<?>) {
            return Optional.of(value.getClass().getSimpleName() + "." + value);
        }

        return Optional.ofNullable(CODE_CONVERTERS_MAP.get(value.getClass()))
                .map(valueCodeConverter -> valueCodeConverter.convert(value));
    }
}
