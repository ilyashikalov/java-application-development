package com.acme.dbo.txlog;

import java.util.Objects;

public class Facade {
    public static final String STRING = "String";
    public static final String INT = "Integer";

    private static boolean intStateClean = true;
    private static boolean stringStateClean = true;
    private static int intAccumulator = 0;
    private static String lastString;
    private static String lastType;
    private static int stringCounter = 0;

    public static void log(Object message) {
        System.out.println(LogMessageDecorator.decorate(message));
    }

    public static void log(boolean message) {
        System.out.println(LogMessageDecorator.decorate(message));
    }

    public static void log(char message) {
        System.out.println(LogMessageDecorator.decorate(message));
    }

    public static void log(int message) {
        if (lastType == null) {
            lastType = INT;
        }
        if (!Objects.equals(lastType, INT) || (long) intAccumulator + message > Integer.MAX_VALUE) {
            flush();
        }
        lastType = INT;
        intStateClean = false;
        intAccumulator += message;
    }

    public static void log(byte message) {
        System.out.println(LogMessageDecorator.decorate(message));
    }

    public static void log(String message) {
        if (lastString == null) {
            lastString = message;
        }
        if (lastType == null) {
            lastType = STRING;
        }
        if (!Objects.equals(message, lastString) || !Objects.equals(lastType, STRING)) {
            flush();
        }
        lastType = STRING;
        lastString = message;
        stringCounter++;
        stringStateClean = false;
    }

    public static void flush() {
        if (!stringStateClean) {
            flushString();
        }
        if (!intStateClean) {
            flushInt();
        }
    }

    private static void flushInt() {
        System.out.println(LogMessageDecorator.decorate(intAccumulator));
        intAccumulator = 0;
        intStateClean = true;
    }

    private static void flushString() {
        if (stringCounter == 1) {
            System.out.println(LogMessageDecorator.decorate(lastString));
        } else if (lastString != null) {
            String decorated = LogMessageDecorator.decorate(String.format("%s (x%d)%n", lastString, stringCounter));
            System.out.println(decorated);
        }

        stringCounter = 0;
        lastString = null;
        stringStateClean = true;
    }
}
