package com.acme.dbo.txlog.iteration02;

import com.acme.dbo.txlog.Facade;
import com.acme.dbo.txlog.SysoutCaptureAndAssertionAbility;
import com.acme.dbo.txlog.saver.ConsoleSaver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoggerTest implements SysoutCaptureAndAssertionAbility {
    private Facade facade;
    //region given
    @Before
    public void setUpSystemOut() {
        resetOut();
        captureSysout();
        facade = new Facade(new ConsoleSaver());
    }

    @After
    public void tearDown() {
        resetOut();
    }
    //endregion


    @Test
    public void shouldLogSequentIntegersAsSum() {
        //region when
        facade.log("str 1");
        facade.log(1);
        facade.log(2);
        facade.log("str 2");
        facade.log(0);
        facade.flush();
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
        facade.log("str 1");
        facade.log(10);
        facade.log(Integer.MAX_VALUE);
        facade.log("str 2");
        facade.log(0);
        facade.flush();
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
        facade.log("str 1");
        facade.log((byte) 10);
        facade.log(Byte.MAX_VALUE);
        facade.log("str 2");
        facade.log(0);
        facade.flush();
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
        facade.log("str 1");
        facade.log("str 2");
        facade.log("str 2");
        facade.log(0);
        facade.log("str 2");
        facade.log("str 3");
        facade.log("str 3");
        facade.log("str 3");
        facade.flush();
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