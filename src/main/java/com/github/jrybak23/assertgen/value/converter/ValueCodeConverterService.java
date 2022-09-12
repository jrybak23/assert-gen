package com.github.jrybak23.assertgen.value.converter;

import java.util.List;
import java.util.Optional;

public class ValueCodeConverterService {

    private final List<ValueCodeConverter> valueCodeConverters = createValueCodeConverters();

    private List<ValueCodeConverter> createValueCodeConverters() {
        return List.of(
                new NullValueCodeConverter(),
                new CharSequenceValueCodeConverter(),
                new LiteralNumberValueCodeConverter(),
                new SimpleValueCodeConverter(),
                new CharValueCodeConverter(),
                new EnumValueCodeConverter(),
                new ParsableTemporalValueCodeConverter(),
                new OptionalValueCodeConverter(this),
                new ConvertableMapEntryValueCodeConverter(this)
        );
    }

    public boolean canConvert(Object object) {
        return valueCodeConverters.stream()
                .anyMatch(valueCodeConverter -> valueCodeConverter.isSuitableFor(object));
    }

    public Optional<String> convertValueToCode(Object value) {
        return valueCodeConverters.stream()
                .filter(valueCodeConverter -> valueCodeConverter.isSuitableFor(value))
                .findFirst()
                .map(valueCodeConverter -> valueCodeConverter.convert(value));
    }
}
