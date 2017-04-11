package com.usana.autotom;

import org.testng.Reporter;

/**
 * Created by kerrysouthworth on 1/6/17.
 */
public final class Util
{
    public static void waitMilliseconds(Timeout timeout, String reasonForWait)
    {
        waitMilliseconds(timeout.getRemainingTime(), reasonForWait);
    }

    public static void waitMilliseconds(long milliseconds, String reasonForWait)
    {
        if (reasonForWait != null && Config.DEBUG)
        {
            Util.log("%s: %d ms", reasonForWait, milliseconds);
        }
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException ex)
        {
            // Do nothing as this exception is expected;
        }
    }

    public static void log(String msg)
    {
        Reporter.log(msg, true);
    }

    public static void log(String msg, Object... args)
    {
        Reporter.log(String.format(msg, (Object[]) args), true);
    }

}
