package com.usana.autotom;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.UUID;

/**
 * Created by kerrysouthworth on 3/14/17.
 */
@NodeEntity
public class ValidInput implements TomElement
{
    @GraphId
    private Long id;
    private String type;
    private String key;
    private String identifier;
    private String uuid;


    private ValidInput(){
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public ValidInput(String type, String identifier){
        this.type = type;
        this.identifier = new String(identifier);
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public void execute(WebDriver webDriver)
    {
        WebElement element = (new WebDriverWait(webDriver,20)).until(new ExpectedCondition<WebElement>()
        {
            @Override
            public WebElement apply(WebDriver d)
            {
                return d.findElement(By.id(key));
            }
        });

        element.sendKeys(getMainPartNumber());
        element.sendKeys(Keys.RETURN);

        // need to pause for lookup after each product is entered
/*        long startTime = System.currentTimeMillis();
        while(true){
            if((startTime+(1000*1)) < System.currentTimeMillis()){
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                break;
            }
        }*/
    }

    public void execute(WebDriver webDriver, String threadId){
        WebElement element = (new WebDriverWait(webDriver,20)).until(new ExpectedCondition<WebElement>()
        {
            @Override
            public WebElement apply(WebDriver d)
            {
                return d.findElement(By.id(key));
            }
        });
        element.sendKeys(threadId);
    }


    public String getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = new String(identifier);
    }

    private String getMainPartNumber() // TODO: this should be deleted in favor of altering the part numbers in the graph
    {
        String identifier = getIdentifier();
        return identifier.substring(0,identifier.indexOf(".")+1);
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Long getId(){ return this.id; }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getName(){ return this.type + " " + this.identifier; }

    public String getUuid(){ return this.uuid; }

}
