package com.acme.dbo.txlog.iteration02;

import com.acme.dbo.txlog.Facade;
import com.acme.dbo.txlog.SysoutCaptureAndAssertionAbility;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class LoggerTest implements SysoutCaptureAndAssertionAbility {
    //region given
    @Before
    public void setUpSystemOut() {
        resetOut();
        captureSysout();
    }

    @After
    public void tearDown() {
        resetOut();
    }
    //endregion


    @Test
    public void shouldLogSequentIntegersAsSum() {
        //region when
        Facade.log("str 1");
        Facade.log(1);
        Facade.log(2);
        Facade.log("str 2");
        Facade.log(0);
        Facade.flush();
        //endregion

        //region then
        assertSysoutContains("str 1");
        assertSysoutContains("3");
        assertSysoutContains("str 2");
        assertSysoutContains("0");
        //endregion
    }

    @Test
    public void shouldLogCorrectlyIntegerOverflowWhenSequentIntegers() {
        //region when
        Facade.log("str 1");
        Facade.log(10);
        Facade.log(Integer.MAX_VALUE);
        Facade.log("str 2");
        Facade.log(0);
        Facade.flush();
        //endregion

        //region then
        assertSysoutContains(String.format("str 1%n"));
        assertSysoutContains(String.format("10%n"));
        assertSysoutContains(String.format(Integer.MAX_VALUE + "%n"));
        assertSysoutContains(String.format("str 2%n"));
        assertSysoutContains(String.format("0%n"));
        //endregion
    }

    @Test
    public void shouldLogCorrectlyByteOverflowWhenSequentBytes() {
        //region when
        Facade.log("str 1");
        Facade.log((byte) 10);
        Facade.log(Byte.MAX_VALUE);
        Facade.log("str 2");
        Facade.log(0);
        Facade.flush();
        //endregion

        //region then
        assertSysoutContains(String.format("str 1%n"));
        assertSysoutContains(String.format("10%n"));
        assertSysoutContains(String.format(Byte.MAX_VALUE + "%n"));
        assertSysoutContains(String.format("str 2%n"));
        assertSysoutContains(String.format("0%n"));
        //endregion
    }

    @Test
    public void shouldLogSameSubsequentStringsWithoutRepeat() {
        //region when
        Facade.log("str 1");
        Facade.log("str 2");
        Facade.log("str 2");
        Facade.log(0);
        Facade.log("str 2");
        Facade.log("str 3");
        Facade.log("str 3");
        Facade.log("str 3");
        Facade.flush();
        //endregion

        //region then
        assertSysoutContains(String.format("str 1%n"));
        assertSysoutContains(String.format("str 2 (x2)%n"));
        assertSysoutContains(String.format("0%n"));
        assertSysoutContains(String.format("str 2%n"));
        assertSysoutContains(String.format("str 3 (x3)%n"));
        //endregion
    }

}