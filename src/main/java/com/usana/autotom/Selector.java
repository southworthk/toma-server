package com.usana.autotom;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.UUID;

/**
 * Created by kerrysouthworth on 1/6/17.
 */

@NodeEntity
public class Selector implements TomElement
{
    @GraphId
    private Long id;

    private String name;
    private final StringBuilder xpath = new StringBuilder();
    private boolean useCSS = true;
    private final StringBuilder css = new StringBuilder();
    private boolean useId = false;
    private String elementId;
    private String uuid;

    private Selector(){
        // Empty constructor required as of Neo4j API 2.0.5
    }

    /**
     * Find an element by its ID.
     * Search all decendants for the element
     *
     * @param id ID of the element
     */
    public Selector(String id)
    {
        this(Scope.DESCENDANTS, TagName.ANY, Attribute.ID, Compare.EQUALS, id);
        this.uuid = UUID.randomUUID().toString();
    }

    public Selector(Scope scope, TagName tagName)
    {
        xpath.append('.');
        setScope(scope);
        xpath.append(tagName.toXpathFormat());
        if (useCSS)
        {
            css.append(tagName.toCssFormat());
        }
    }

    public Selector(Scope scope, TagName tagName, Attribute attribute, Compare compare)
    {
        this(scope, tagName);
        addAttribute(attribute, compare, null);
    }

    public Selector(Selector selector)
    {
        useCSS = selector.useCSS;
        useId = selector.useId;
        elementId = selector.elementId;
        xpath.append(selector.xpath.toString());
        css.append(selector.css.toString());
    }

    public Selector(Scope scope, TagName tagName, Attribute attribute, Compare compare, String value)
    {
        this(scope, tagName);
        addAttribute(attribute, compare, value);
        if(attribute == Attribute.ID && compare == Compare.EQUALS)
        {
            useId = true;
            elementId = value;
        }
    }


    @Override
    public void execute(WebDriver driver)
    {

    }

    private void setScope(Scope scope)
    {
        if (scope == Scope.CHILDREN)
        {
            xpath.append("/");
            useCSS = false;
        }
        else
        {
            xpath.append("//");
            css.append(" ");
        }
    }

    private void addAttribute(Attribute attribute, Compare compare, String value)
    {
        xpath.append(compare.xPathFormat(attribute, value));
        if (useCSS)
        {
            css.append(compare.cssFormat(attribute, value));
        }
    }

    public Selector sibling(int index)
    {
        useId = false;
        xpath.append(String.format("[%d]", index));
        if (useCSS)
        {
            css.append(String.format(":nth-child(%d)", index));
        }
        return this;
    }

    public Selector and(Attribute attribute, Compare compare)
    {
        useId = false;
        addAttribute(attribute, compare, null);
        return this;
    }
    public Selector and(Attribute attribute, Compare compare, String value)
    {
        useId = false;
        addAttribute(attribute, compare, value);
        return this;
    }

    public Selector or(Attribute attribute, Compare compare)
    {
        return or(attribute, compare, null);
    }

    public Selector or(Attribute attribute, Compare compare, String value)
    {
        useId = false;
        useCSS = false;
        int offset = xpath.length() - 1;
        xpath.insert(offset, " or ");
        xpath.insert(offset + 4, compare.xPath(attribute, value));
        return this;
    }

    public Selector or(String id)
    {
        return or(Attribute.ID, Compare.EQUALS, id);
    }

    public Selector parent()
    {
        useId = false;
        useCSS = false;
        xpath.append("/..");
        return this;
    }

    public Selector descendant(String id)
    {
        return descendant(Scope.DESCENDANTS, TagName.ANY, Attribute.ID, Compare.EQUALS, id);
    }

    public Selector descendant(Scope scope, TagName tagName)
    {
        return descendant(scope, tagName, null, null, null);
    }

    public Selector descendant(Scope scope, TagName tagName, Attribute attribute, Compare compare)
    {
        return descendant(scope, tagName, attribute, compare, null);
    }

    public Selector descendant(Scope scope, TagName tagName, Attribute attribute, Compare compare, String value)
    {
        useId = false;
        setScope(scope);
        xpath.append(tagName.toXpathFormat());
        if (useCSS)
        {
            css.append(tagName.toCssFormat());
        }
        if (compare != null)
        {
            addAttribute(attribute, compare, value);
        }
        return this;
    }

    public Selector alternate(String id)
    {
        return alternate(Scope.DESCENDANTS, TagName.ANY, Attribute.ID, Compare.EQUALS, id);
    }

    public Selector alternate(Scope scope, TagName tagName)
    {
        return alternate(scope, tagName, null, null, null);
    }

    public Selector alternate(Scope scope, TagName tagName, Attribute attribute, Compare compare)
    {
        return alternate(scope, tagName, attribute, compare, null);
    }

    public Selector alternate(Scope scope, TagName tagName, Attribute attribute, Compare compare, String value)
    {
        useId = false;
        xpath.append(" | .");
        if (useCSS)
        {
            css.append(" , ");
        }
        setScope(scope);
        xpath.append(tagName.toXpathFormat());
        if (useCSS)
        {
            css.append(tagName.toCssFormat());
        }
        if (attribute != null)
        {
            addAttribute(attribute, compare, value);
        }
        return this;
    }

    public void disableCSS()
    {
        useCSS = false;
    }

    By getConstraint()
    {
        By constraint;
        if(useId)
        {
            constraint = By.id(elementId.trim());
        }
        else if (useCSS)
        {
            constraint = By.cssSelector(css.toString().trim());
        }
        else
        {
            constraint = By.xpath(xpath.toString());
        }
        return constraint;
    }

    /**
     * Method to return the selector as a string
     *
     * @return The current method to find an element
     */
    @Override
    public String toString()
    {
        String stg;

        if(useId)
        {
            stg = elementId;
        }
        else if (useCSS)
        {
            stg = css.toString();
        }
        else
        {
            stg = xpath.toString();
        }
        return stg;
    }

    public Long getId(){ return this.id; }

    public String getName(){ return this.name; }

    public String getUuid(){ return this.uuid; }

}
