package com.github.jrybak23.assertgen;

import java.util.Arrays;

public final class StringUtils {

    public static String escapeChars(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("'", "\\'")
                .replace("\"", "\\\"");
    }

    public static boolean isMultiline(String string) {
        return Arrays.stream(string.split("\n"))
                .filter(s -> !s.isEmpty())
                .count() > 1;
    }

    private StringUtils() {
    }
}
