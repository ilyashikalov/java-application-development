package com.acme.dbo.txlog.service;

import com.acme.dbo.txlog.message.IntMessage;
import com.acme.dbo.txlog.message.StringMessage;
import com.acme.dbo.txlog.saver.Saver;

public class LogService {

    private final Saver saver;

    public LogService(Saver saver) {
        this.saver = saver;
    }

    public void log(IntMessage message) {

    }

    public void log(StringMessage message) {

    }
}