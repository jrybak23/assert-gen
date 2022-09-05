package com.github.jrybak23.assertgen.call.experession;

class RootCall implements Call {

    private final String reference;

    public RootCall(String reference) {
        this.reference = reference;
    }

    @Override
    public String getName() {
        return reference;
    }

    @Override
    public String toCode() {
        return reference;
    }
}
