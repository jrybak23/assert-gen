package com.github.jrybak23.assertgen.call.experession;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CallExpression {

    public static CallExpression ofReference(String reference) {
        Call call = new RootCall(reference);
        return new CallExpression(List.of(call));
    }

    private final List<Call> calls;

    public CallExpression addMethodCall(Method method) {
        Call call = new MethodCall(method);
        List<Call> newCalls = addNewCall(call);
        return new CallExpression(newCalls);
    }

    public String getNameOfLastCall() {
        return calls.get(calls.size() - 1).getName();
    }

    private List<Call> addNewCall(Call call) {
        return Stream.concat(getCalls().stream(), Stream.of(call))
                .toList();
    }

    @Override
    public String toString() {
        return calls.stream()
                .map(Call::toCode)
                .collect(joining());
    }

    public boolean methodWasNotCalled(Method method) {
        return calls.stream()
                .filter(call -> call instanceof MethodCall)
                .map(call -> (MethodCall) call)
                .noneMatch(methodCall -> methodCall.is(method));
    }
}
