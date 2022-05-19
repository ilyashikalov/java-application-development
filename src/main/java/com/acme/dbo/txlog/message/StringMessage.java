package com.acme.dbo.txlog.message;

public class StringMessage extends Message{
    private final String message;

    public StringMessage(String message) {
        this.message = message;
    }

    @Override
    public String decorate() {
        return "string: " + message;
    }


    @Override
    public String getMessage() {
        return message;
    }

}