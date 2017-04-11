package com.usana.autotom;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by kerrysouthworth on 1/5/17.
 */
@NodeEntity
public class Hyperlink implements TomElement
{
    @GraphId
    private Long id;

    private String name;
    private String href;
    private String uuid;

    private Hyperlink(){
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public Hyperlink(String name, String href)
    {
        this.name = name;
        this.href = href;
        this.uuid = UUID.randomUUID().toString();
    }

    @Relationship(type = "LAUNCHES", direction = Relationship.OUTGOING)
    public Form launchForm;

    public void launches(Form form){
        if (this.launchForm == null){
            this.launchForm = form;
        }
    }

//    @Override
//    public void execute(WebDriver webDriver)
//    {
//        WebElement element = (new WebDriverWait(webDriver,20)).until(new ExpectedCondition<WebElement>()
//        {
//            @Override
//            public WebElement apply(WebDriver d)
//            {
//                return d.findElement(By.xpath("//a[@href='"+getHref()+"']"));
//            }
//        });
//
//
//        Actions builder = new Actions(webDriver);
//        builder.click(element);
//        builder.build().perform(); .enroll
//    }

    @Override
    public void execute(WebDriver webDriver){

        WebElement element = webDriver.findElement(By.xpath("//a[@href='/frame/shopSecureForward.jsp?url=oe']"));
        Actions builder = new Actions(webDriver);
        //builder.moveByOffset(-35,-35).click().perform();
        //builder.click().perform();
        webDriver.navigate().to("https://www.usanabeta.com/frame/shopSecureForward.jsp?url=oe");

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getHref()
    {
        return href;
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public Long getId(){ return this.id; }

    public String getUuid(){ return this.uuid; }
}
