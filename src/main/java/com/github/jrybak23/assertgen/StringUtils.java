package com.github.jrybak23.assertgen;

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

    private StringUtils() {
    }
}
