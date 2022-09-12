package com.github.jrybak23.assertgen.value.converter;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
class ConvertableMapEntryValueCodeConverter implements ValueCodeConverter {

    private final ValueCodeConverterService valueCodeConverterService;

    @Override
    public boolean isSuitableFor(Object value) {
        return value instanceof Map.Entry<?, ?> entry
                && valueCodeConverterService.canConvert(entry.getKey())
                && valueCodeConverterService.canConvert(entry.getValue());
    }

    @Override
    public String convert(Object value) {
        Map.Entry<?, ?> entry = (Map.Entry<?, ?>) value;
        String convertedKey = valueCodeConverterService.convertValueToCode(entry.getKey()).orElseThrow();
        String convertedValue = valueCodeConverterService.convertValueToCode(entry.getValue()).orElseThrow();
        return "Map.entry(" + convertedKey + ", " + convertedValue + ")";
    }
}
