package com.github.jrybak23.assertgen.call.experession;

import java.lang.reflect.Method;

class MethodCall implements Call {

    private final Method method;

    public MethodCall(Method method) {
        this.method = method;
    }

    @Override
    public String getName() {
        return method.getName();
    }

    @Override
    public String toCode() {
        return "." + method.getName() + "()";
    }

    public boolean is(Method method) {
        return this.method.equals(method);
    }
}
