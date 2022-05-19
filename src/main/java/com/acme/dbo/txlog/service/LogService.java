package com.acme.dbo.txlog.service;

import com.acme.dbo.txlog.message.IntMessage;
import com.acme.dbo.txlog.message.StringMessage;
import com.acme.dbo.txlog.saver.Saver;

import java.util.Objects;

public class LogService {

    public static final String STRING = "String";
    public static final String INT = "Integer";

    private static boolean intStateClean = true;
    private static boolean stringStateClean = true;
    private static int intAccumulator = 0;
    private static String lastString;
    private static String lastType;
    private static int stringCounter = 0;
    private final Saver saver;

    public LogService(Saver saver) {
        this.saver = saver;
    }

    public void log(IntMessage message) {
        if (lastType == null) {
            lastType = INT;
        }
        if (!Objects.equals(lastType, INT) || (long) intAccumulator + message.getValue() > Integer.MAX_VALUE) {
            flush();
        }
        lastType = INT;
        intStateClean = false;
        intAccumulator += message.getValue();
    }

    public void log(StringMessage message) {
        if (lastString == null) {
            lastString = message.getMessage();
        }
        if (lastType == null) {
            lastType = STRING;
        }
        if (!message.getMessage().equals(lastString) || !Objects.equals(lastType, STRING)) {
            flush();
        }
        lastType = STRING;
        lastString = message.getMessage();
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
        IntMessage intMessage = new IntMessage(intAccumulator);
        saver.save(intMessage.decorate());
        intAccumulator = 0;
        intStateClean = true;
    }

    private void flushString() {
        StringMessage stringMessage = new StringMessage(stringCounter == 1 ? lastString : String.format("%s (x%d)%n", lastString, stringCounter));
        saver.save(stringMessage.decorate());

        stringCounter = 0;
        lastString = null;
        stringStateClean = true;
    }

    public void log(String decoratedMessage) {
        //  Legacy code to support old cases of non-int and non-string messages
        saver.save(decoratedMessage);
    }
}
