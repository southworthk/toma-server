package com.usana.autotom;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.openqa.selenium.WebDriver;

import java.util.UUID;

/**
 * Created by kerrysouthworth on 3/30/17.
 */
@NodeEntity
public class Wait implements TomElement
{
    @GraphId
    private Long id;
    private int waitInterval;
    private String uuid;

    public Wait()
    {

    }

    public Wait(int waitInterval)
    {
        this.waitInterval = waitInterval;
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public void execute(WebDriver webDriver)
    {
        long startTime = System.currentTimeMillis();
        int wait = getWaitInterval();
        while(true)
        {
            if ((startTime + (1000 * wait)) < System.currentTimeMillis())
            {
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                break;
            }
        }
    }

    @Override
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public int getWaitInterval()
    {
        return waitInterval;
    }

    public void setWaitInterval(int waitInterval)
    {
        this.waitInterval = waitInterval;
    }

    public String getName(){ return "Wait Interval of "+this.getWaitInterval() + " seconds";}

    public String getUuid(){ return this.uuid; }
}
