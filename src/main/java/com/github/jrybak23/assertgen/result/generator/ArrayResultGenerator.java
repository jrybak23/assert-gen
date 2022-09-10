package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.NameGenerator;
import com.github.jrybak23.assertgen.call.experession.CallExpression;
import com.github.jrybak23.assertgen.value.converter.ValueCodeConverterService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;

public class ArrayResultGenerator extends SpliteratableResultGenerator {

    public ArrayResultGenerator(ValueCodeConverterService valueCodeConverterService, NameGenerator nameGenerator) {
        super(valueCodeConverterService, nameGenerator);
    }

    @Override
    public boolean isSuitable(Object value) {
        return isArray(value);
    }

    private boolean isArray(Object value) {
        return value instanceof Object[]
                || value instanceof byte[]
                || value instanceof short[]
                || value instanceof int[]
                || value instanceof long[]
                || value instanceof float[]
                || value instanceof double[]
                || value instanceof boolean[]
                || value instanceof char[];
    }

    @Override
    Spliterator<?> getSpliterator(Object value) {
        return convertArrayToList(value).spliterator();
    }

    private List<?> convertArrayToList(Object value) {
        if (value instanceof Object[] array) {
            return Arrays.stream(array).toList();
        } else if (value instanceof byte[] array) {
            List<Byte> bytes = new ArrayList<>();
            for (byte b : array) {
                bytes.add(b);
            }
            return bytes;
        } else if (value instanceof short[] array) {
            List<Short> shorts = new ArrayList<>();
            for (short s : array) {
                shorts.add(s);
            }
            return shorts;
        } else if (value instanceof int[] array) {
            return Arrays.stream(array).boxed().toList();
        } else if (value instanceof long[] array) {
            return Arrays.stream(array).boxed().toList();
        } else if (value instanceof float[] array) {
            List<Float> floats = new ArrayList<>();
            for (float f : array) {
                floats.add(f);
            }
            return floats;
        } else if (value instanceof double[] array) {
            return Arrays.stream(array).boxed().toList();
        } else if (value instanceof boolean[] array) {
            List<Boolean> booleans = new ArrayList<>();
            for (boolean bool : array) {
                booleans.add(bool);
            }
            return booleans;
        } else if (value instanceof char[] array) {
            List<Character> characters = new ArrayList<>();
            for (char character : array) {
                characters.add(character);
            }
            return characters;
        }
        throw new RuntimeException();
    }

    @Override
    void appendAssertThatIsEmpty(CodeAppender codeAppender, CallExpression callExpression) {
        codeAppender.appendNewLine("assertThat(" + callExpression + ").isEmpty();");
    }

    @Override
    void appendAssertThat(CodeAppender codeAppender, CallExpression callExpression) {
        codeAppender.appendNewLine("assertThat(" + callExpression + ")");
    }
}
