package com.usana.autotom;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

/**
 * Created by kerrysouthworth on 1/3/17.
 */

@NodeEntity
public class InputElement implements TomElement
{
    @GraphId
    private Long id;

    private String name;
    private String tagId;
    private String type;
    private String description;
    private int maxlength;
    private String key;
    private String value;
    private String uuid;

    private InputElement(){
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public InputElement(String name)
    {
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
    }

    @Relationship(type = "ACCEPTS", direction = Relationship.OUTGOING)
    public Set<TomElement> validInputs;

    public void accepts(TomElement tomElement){
        if(validInputs == null){
            validInputs = new HashSet<>();
        }
        validInputs.add(tomElement);
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
        element.sendKeys(value);
    }

    public List<ValidInput> getValidInputs(TomElementRepository tomElementRepository){
        String inputElementName = this.getName();
        return tomElementRepository.getValidInputElements(inputElementName);
    }

    public ValidInput getRandomValidInput(TomElementRepository tomElementRepository){
        ArrayList<ValidInput> inputs = (ArrayList<ValidInput>) getValidInputs(tomElementRepository);
        int arrayLen = inputs.size();
        Random rand = new Random();
        return inputs.get(rand.nextInt(arrayLen));
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

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getMaxlength()
    {
        return maxlength;
    }

    public void setMaxlength(int maxlength)
    {
        this.maxlength = maxlength;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
