package com.acme.dbo.txlog.decorator;

public class LogMessageDecorator {
    public static <T> String decorate (T message) {
        return getPrefix(message) + message;
    }
    private static <T> String getPrefix (T message) {
        if (message == null) {
            return "null";
        }
        switch (message.getClass().getSimpleName()) {
            case "Byte" :
            case "Boolean" :
                return "primitive: ";
            case "Character" : return "char: ";
            case "Object" : return "reference: ";
        }
        return "";
    }
}
