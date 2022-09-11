package com.github.jrybak23.assertgen.result.generator;

import com.github.jrybak23.assertgen.CodeAppender;
import com.github.jrybak23.assertgen.call.experession.CallExpression;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
abstract class SpecificMethodInvocationResultGenerator implements ResultGenerator {

    private final ResultGeneratorService resultGeneratorService;

    @Override
    public void generateCode(CodeAppender codeAppender, CallExpression callExpression, Object value) {
        getMethodsToInvoke().stream()
                .map(methodName -> MethodUtils.getMethod(value.getClass(), methodName))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(method -> {
                    CallExpression methodCall = callExpression.addMethodCall(method);
                    Object result = MethodUtils.invokeMethod(value, method);
                    resultGeneratorService.generate(codeAppender, methodCall, result);
                });
    }

    abstract List<String> getMethodsToInvoke();
}
