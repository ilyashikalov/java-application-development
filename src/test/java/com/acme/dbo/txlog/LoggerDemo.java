package com.acme.dbo.txlog;

import com.acme.dbo.txlog.message.IntMessage;
import com.acme.dbo.txlog.message.Message;
import com.acme.dbo.txlog.saver.ConsoleSaver;

import static com.acme.dbo.txlog.message.Severity.CRITICAL;

public class LoggerDemo {
    public static void main(String[] args) {
        Facade facade = new Facade (new ConsoleSaver());
        Message m = new IntMessage();
        m.setMessage("qwe");
        m.setSeverity(CRITICAL);
    }
}
