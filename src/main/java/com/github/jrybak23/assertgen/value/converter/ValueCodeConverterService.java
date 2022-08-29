package com.github.jrybak23.assertgen.value.converter;

import java.util.List;
import java.util.Optional;

public class ValueCodeConverterService {

    private static final List<ValueCodeConverter> VALUE_CODE_CONVERTERS = List.of(
            new StringValueCodeConverter(),
            new LiteralNumberValueCodeConverter(),
            new SimpleValueCodeConverter(),
            new EnumValueCodeConverter(),
            new ParsableTemporalValueCodeConverter()
    );

    public boolean canConvert(Object object) {
        return VALUE_CODE_CONVERTERS.stream()
                .anyMatch(valueCodeConverter -> valueCodeConverter.isSuitableFor(object));
    }

    public Optional<String> convertValueToCode(Object value) {
        return VALUE_CODE_CONVERTERS.stream()
                .filter(valueCodeConverter -> valueCodeConverter.isSuitableFor(value))
                .findFirst()
                .map(valueCodeConverter -> valueCodeConverter.convert(value));
    }
}
