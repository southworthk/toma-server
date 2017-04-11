package com.usana.autotom;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.UUID;

/**
 * Created by kerrysouthworth on 1/5/17.
 */
@NodeEntity
public class Button implements TomElement
{
    @GraphId
    private Long id;

    private String name;
    private String tagId;
    private String uuid;

    private Button(){
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public Button(String name, String tagId)
    {
        this.name = name;
        this.tagId = tagId;
        this.uuid = UUID.randomUUID().toString();
    }

    @Relationship(type = "LAUNCHES", direction = Relationship.OUTGOING)
    public Form launchForm;

    public void launches(Form form){
        if (this.launchForm == null){
            this.launchForm = form;
        }
    }

    @Override
    public void execute(WebDriver webDriver){
        webDriver.findElement(By.id(getTagId())).click();
    }

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

    public Long getId(){ return this.id; };

    public String getUuid(){ return this.uuid; }
}
