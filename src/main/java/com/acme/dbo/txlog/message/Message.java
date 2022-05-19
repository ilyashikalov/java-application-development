package com.acme.dbo.txlog.message;

public abstract class Message {
    private String message;
    private Severity severity;

    public abstract String decorate();

    public String getMessage() {
        return message;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

}
