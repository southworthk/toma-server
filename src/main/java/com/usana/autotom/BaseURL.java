package com.usana.autotom;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.openqa.selenium.WebDriver;

import java.util.UUID;

/**
 * Created by kerrysouthworth on 1/3/17.
 */

@NodeEntity
public class BaseURL implements TomElement
{
    @GraphId
    private Long id;

    private String name;
    private String url;
    private String uuid;

    private BaseURL(){
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public BaseURL(String name, String url) {
        this.name = name;
        this.url = url;
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
    public void execute(WebDriver driver)
    {
        driver.get(getUrl());
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public Long getId(){ return this.id; };

    public String getUuid(){ return this.uuid; }
}
