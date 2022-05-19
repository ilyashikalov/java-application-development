package com.acme.dbo.txlog.message;

public class StringMessage extends Message{
    @Override
    public String decorate() {
        return "string: " + getMessage();
    }
}