package com.github.jrybak23.assertgen.call.experession;

class MethodCall implements Call {

    private final String methodName;

    public MethodCall(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String getName() {
        return methodName;
    }

    @Override
    public String toCode() {
        return "." + methodName + "()";
    }
}
