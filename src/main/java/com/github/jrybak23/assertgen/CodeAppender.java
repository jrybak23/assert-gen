package com.github.jrybak23.assertgen;

import java.util.function.Consumer;

public class CodeAppender {

    private final static int INDENT = 8;
    private int currentIndent = 0;

    private final StringBuilder result = new StringBuilder();

    public void sameIndent(Runnable runnable) {
        addIndent();
        runnable.run();
        removeIndent();
    }

    private void addIndent() {
        currentIndent += INDENT;
    }

    private void removeIndent() {
        currentIndent -= INDENT;
    }

    public void appendNewLine(String line) {
        result.append(" ".repeat(currentIndent)).append(line).append("\n");
    }

    public String getResult() {
        return result.toString();
    }
}
