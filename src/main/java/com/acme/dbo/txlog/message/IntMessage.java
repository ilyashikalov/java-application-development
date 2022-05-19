package com.acme.dbo.txlog.message;

public class IntMessage extends Message {
    private final int value;

    public IntMessage(int value) {
        this.value = value;
    }

    @Override
    public String decorate() {
        return "primitive: " + getMessage();
    }

    @Override
    public String getMessage() {
        return Integer.toString(value);
    }

    public int getValue() {
        return value;
    }
}
