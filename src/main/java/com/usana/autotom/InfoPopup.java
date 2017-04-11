package com.usana.autotom;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.UUID;

/**
 * Created by kerrysouthworth on 1/6/17.
 */

@NodeEntity
public class InfoPopup implements TomElement
{
    @GraphId
    private Long id;

    private String name;
    private String key;
    private String value;
    private String uuid;

    private InfoPopup(){
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public InfoPopup(String name)
    {
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
    }

    @Relationship(type = "LAUNCHES", direction = Relationship.OUTGOING)
    public TomElement launchForm;

    public void launches(TomElement form){
        if (this.launchForm == null){
            this.launchForm = form;
        }
    }

    @Override
    public void execute(WebDriver webDriver)
    {
        WebElement element = (new WebDriverWait(webDriver,20)).until(new ExpectedCondition<WebElement>()
        {
            @Override
            public WebElement apply(WebDriver d)
            {
                return d.findElement(By.className(key));
            }
        });
        element.click();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public Long getId(){ return this.id; }

    public String getUuid(){ return this.uuid; }

}
