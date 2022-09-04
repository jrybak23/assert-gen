package com.github.jrybak23.assertgen.value.converter;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
class OptionalValueCodeConverter implements ValueCodeConverter {

    private final ValueCodeConverterService valueCodeConverterService;

    @Override
    public boolean isSuitableFor(Object value) {
        return value instanceof Optional<?> optional
                && (optional.isEmpty() || valueCodeConverterService.canConvert(optional.get()));
    }

    @Override
    public String convert(Object value) {
        Optional<?> optional = (Optional<?>) value;
        if (optional.isEmpty()) {
            return "Optional.empty()";
        }
        String convertedValue = valueCodeConverterService.convertValueToCode(optional.get()).orElseThrow();
        return "Optional.of(" + convertedValue + ")";
    }
}
