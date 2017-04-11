package com.usana.autotom;


import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


/**
 * Created by kerrysouthworth on 3/31/17.
 */

@NodeEntity
public class PrintDialog implements TomElement
{
    @GraphId
    private Long id;
    private String name;
    private String tagId;
    private String uuid;


    public PrintDialog()
    {
        // Neo4J requires an empty constructor
    }

    public PrintDialog(String name, String tagId)
    {
        this.name = name;
        this.tagId = tagId;
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public void execute(WebDriver webDriver)
    {
       System.setProperty("java.awt.headless", "false");

        try
        {
            long startTime = System.currentTimeMillis();
            while(true){
                if((startTime+(1000*120)) < System.currentTimeMillis()){
                    break;
                }
            }

            Robot robot = new Robot();
            robot.setAutoDelay(40);
            robot.setAutoWaitForIdle(true);

            robot.delay(4000);
            robot.mouseMove(860,410);
            leftClick(robot);
            robot.delay(500);
            leftClick(robot);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        // get the print button
/*        WebElement print_button = webDriver.findElement(By.cssSelector("..."));

        // click on the print button and wait for print to be called
        webDriver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
        ((JavascriptExecutor)webDriver).executeAsyncScript(
                "var callback = arguments[1];" +
                        "window.print = function(){callback();};" +
                        "arguments[0].click();"
                , print_button);
                */
    }

    private void leftClick(Robot robot)
    {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(200);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(200);
    }

    public Long getId(){ return this.id; }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTagId()
    {
        return tagId;
    }

    public void setTagId(String tagId)
    {
        this.tagId = tagId;
    }

    public String getUuid(){ return this.uuid; }
}
