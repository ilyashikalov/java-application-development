package com.acme.dbo.txlog.saver;

public class ConsoleSaver implements Saver {
    public void save(String decoratedMessage) {
        System.out.println(decoratedMessage);
    }
}
