package com.usana.autotom;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by kerrysouthworth on 1/3/17.
 */

@NodeEntity
public class Form implements TomElement
{
    @GraphId
    private Long id;

    private String name;
    private String uuid;

    private Form(){
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public Form(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
    }

    @Relationship(type = "CONTAINS", direction = Relationship.OUTGOING)
    public Set<TomElement> formElements;

    public void contains(TomElement tomElement){
        if(formElements == null){
            formElements = new HashSet<>();
        }
        formElements.add(tomElement);
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
        WebElement loginForm = (new WebDriverWait(webDriver,20)).until(new ExpectedCondition<WebElement>()
        {
            @Override
            public WebElement apply(WebDriver d)
            {
                return d.findElement(By.name(getName()));
            }
        });
        loginForm.submit();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<InputElement> getInputElements(TomElementRepository tomElementRepository){
        String formName = this.getName();
        return tomElementRepository.getInputElements(formName);
    }

    public List<SelectList> getSelectElements(TomElementRepository tomElementRepository){
        String formName = this.getName();
        return tomElementRepository.getSelectElements(formName);
    }

    public Button getFormButton(TomElementRepository tomElementRepository){
        return tomElementRepository.getButton(this.getName());
    }

    public Long getId(){ return this.id; }

    public String getUuid(){ return this.uuid; }

}
