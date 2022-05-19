package com.acme.dbo.txlog.message;

public class IntMessage extends Message {
    @Override
    public String decorate() {
        return "primitive: " + getMessage();
    }
}
