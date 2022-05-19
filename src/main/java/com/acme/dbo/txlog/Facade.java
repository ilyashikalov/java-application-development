package com.acme.dbo.txlog;

import com.acme.dbo.txlog.decorator.LogMessageDecorator;
import com.acme.dbo.txlog.message.IntMessage;
import com.acme.dbo.txlog.message.StringMessage;
import com.acme.dbo.txlog.saver.Saver;
import com.acme.dbo.txlog.service.LogService;

public class Facade {
    private final LogService logService;

    public Facade(Saver consoleSaver) {
        logService = new LogService(consoleSaver);
    }

    public void log(Object message) {
        logService.log(LogMessageDecorator.decorate(message));
    }

    public void log(boolean message) {
        logService.log(LogMessageDecorator.decorate(message));
    }

    public void log(char message) {
        logService.log(LogMessageDecorator.decorate(message));
    }

    public void log(int message) {
        IntMessage intMessage = new IntMessage(message);
        logService.log(intMessage);
    }

    public void log(byte message) {
        logService.log(LogMessageDecorator.decorate(message));
    }

    public void log(String message) {
        StringMessage stringMessage = new StringMessage(message);
        logService.log(stringMessage);
    }

    public void flush() {
        logService.flush();
    }
}
