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
public class Option implements TomElement
{
    @GraphId
    private Long id;

    private String name;
    private String value;
    private String uuid;

    private Option(){
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public Option(String name, String value)
    {
        this.name = name;
        this.value = value;
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public void execute(WebDriver driver)
    {

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
