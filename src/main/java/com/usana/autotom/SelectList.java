package com.usana.autotom;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.base.Predicate;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by kerrysouthworth on 1/3/17.
 */

@NodeEntity
public class SelectList implements TomElement
{
    @GraphId
    private Long id;

    private String name;
    private String tagId;
    private String description;
    private String value;
    private int index;
    private String visibleText;
    private String uuid;

    private SelectList(){
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public SelectList(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
    }

    @Relationship (type = "CONTAINS", direction = Relationship.OUTGOING)
    public Set<TomElement> options;

    public void contains(TomElement tomElement){
        if(options == null){
            options = new HashSet<>();
        }
        options.add(tomElement);
    }


    @Override
    public void execute(WebDriver webDriver)
    {
        webDriver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        Select droplist = (new WebDriverWait(webDriver,20)).ignoring(NoSuchElementException.class).until(new ExpectedCondition<Select>()
        {
            @Nullable
            @Override
            public Select apply(@Nullable WebDriver webDriver)
            {
                return new Select(webDriver.findElement(By.id(getTagId())));
            }
        });

        new FluentWait<WebDriver>(webDriver)
                .withTimeout(60, TimeUnit.SECONDS)
                .pollingEvery(10, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(new Predicate<WebDriver>() {

                    public boolean apply(WebDriver d) {
                        return (!droplist.getOptions().isEmpty());
                    }
                });

        long startTime = System.currentTimeMillis();
        while(true){
            if((startTime+(1000*3)) < System.currentTimeMillis()){
                break;
            }
        }

        droplist.selectByIndex(getIndex());

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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getVisibleText()
    {
        return visibleText;
    }

    public void setVisibleText(String visibleText)
    {
        this.visibleText = visibleText;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public Long getId(){ return this.id; }

    public String getUuid(){ return this.uuid; }
}
