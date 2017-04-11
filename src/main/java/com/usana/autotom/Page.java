package com.usana.autotom;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.openqa.selenium.WebDriver;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by kerrysouthworth on 1/5/17.
 */
@NodeEntity
public class Page implements TomElement
{
    @GraphId
    private Long id;

    private String name;
    private String uuid;

    private Page(){
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public Page(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
    }

    @Relationship (type = "CONTAINS", direction = Relationship.OUTGOING)
    public Set<TomElement> pageElements;

    public void contains(TomElement tomElement){
        if(pageElements == null){
            pageElements = new HashSet<>();
        }
        pageElements.add(tomElement);
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

    public Long getId(){ return this.id; }

    public String getUuid(){ return this.uuid; }
}
