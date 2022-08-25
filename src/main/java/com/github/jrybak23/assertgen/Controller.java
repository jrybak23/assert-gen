package com.github.jrybak23.assertgen;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Controller {

    private final CodeGenerationService service;
    private final Object inputObject;

    public String getResultCode() {
        return service.generateCode(inputObject);
    }
}
