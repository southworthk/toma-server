package com.usana.autotom;

/**
 * Created by kerrysouthworth on 1/6/17.
 */
public class Timeout
{
    private int originalTime;
    private int remainingTime;

    public Timeout(int seconds)
    {
        originalTime = seconds * 1000;
        remainingTime = originalTime;
    }

    public boolean wait(int waitMilliseconds)
    {
        boolean result;
        if (remainingTime > 0)
        {
            waitMilliseconds = waitMilliseconds > remainingTime ? remainingTime : waitMilliseconds; // wait at least the remaining time
            Util.waitMilliseconds(waitMilliseconds, null);
            remainingTime -= waitMilliseconds;
            result = true;
        }
        else
        {
            result = false;
        }
        return result;
    }

    public void expire()
    {
        remainingTime = 0;
    }

    public int getOriginalTime()
    {
        return originalTime;
    }

    public int getRemainingTime()
    {
        return remainingTime;
    }

    public void commuteOriginalTime()
    {
        originalTime = remainingTime;
    }

    public int getUsedTime()
    {
        return originalTime - remainingTime;
    }

    public void reset()
    {
        remainingTime = originalTime;
    }

    @Override
    public String toString()
    {
        return String.format("Original: %d ms, Remaining: %d ms", originalTime, remainingTime);
    }
}
