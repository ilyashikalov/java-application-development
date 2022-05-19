package com.acme.dbo.txlog;

import com.acme.dbo.txlog.decorator.LogMessageDecorator;
import com.acme.dbo.txlog.saver.ConsoleSaver;

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
    private final ConsoleSaver saver;

    public Facade(ConsoleSaver consoleSaver) {
        saver = consoleSaver;
    }

    public void log(Object message) {
        saver.save(LogMessageDecorator.decorate(message));
    }

    public void log(boolean message) {
        saver.save(LogMessageDecorator.decorate(message));
    }

    public void log(char message) {
        saver.save(LogMessageDecorator.decorate(message));
    }

    public void log(int message) {
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

    public void log(byte message) {
        saver.save(LogMessageDecorator.decorate(message));
    }

    public void log(String message) {
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

    public void flush() {
        if (!stringStateClean) {
            flushString();
        }
        if (!intStateClean) {
            flushInt();
        }
    }

    private void flushInt() {
        saver.save(LogMessageDecorator.decorate(intAccumulator));
        intAccumulator = 0;
        intStateClean = true;
    }

    private void flushString() {
        if (stringCounter == 1) {
            saver.save(LogMessageDecorator.decorate(lastString));
        } else if (lastString != null) {
            String decorated = LogMessageDecorator.decorate(String.format("%s (x%d)%n", lastString, stringCounter));
            saver.save(decorated);
        }

        stringCounter = 0;
        lastString = null;
        stringStateClean = true;
    }
}
