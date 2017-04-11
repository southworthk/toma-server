package com.usana.autotom;

public enum Compare
{
    CONTAINS,
    EXISTS,
    EQUALS,
    NOT_CONTAINS,
    NOT_EXIST,
    NOT_EQUALS;

    public String xPath(Attribute attribute, String value)
    {

        switch(this)
        {
            case EQUALS:
                value = String.format("@%s='%s'", attribute.toAttributeName(), value);
                break;
            case NOT_EQUALS:
                value = String.format("not(@%s='%s')", attribute.toAttributeName(), value);
                break;
            case CONTAINS:
                value = String.format("contains(@%s, '%s')", attribute.toAttributeName(), value);
                break;
            case NOT_CONTAINS:
                value = String.format("not(contains(@%s, '%s'))", attribute.toAttributeName(), value);
                break;
            case EXISTS:
                value = String.format("@%s", attribute.toAttributeName());
                break;
            case NOT_EXIST:
                value = String.format("not(@%s)", attribute.toAttributeName());
                break;
            default:
                throw new IllegalArgumentException();
        }
        return value;}

    public String xPathFormat(Attribute attribute, String value)
    {

        switch(this)
        {
            case EQUALS:
                value = String.format("[%s]", xPath(attribute, value));
                break;
            case NOT_EQUALS:
                value = String.format("[%s]", xPath(attribute, value));
                break;
            case CONTAINS:
                value = String.format("[%s]", xPath(attribute, value));
                break;
            case NOT_CONTAINS:
                value = String.format("[%s]", xPath(attribute, value));
                break;
            case EXISTS:
                value = String.format("[%s]", xPath(attribute, value));
                break;
            case NOT_EXIST:
                value = String.format("[%s]", xPath(attribute, value));
                break;
            default:
                throw new IllegalArgumentException();
        }
        return value;
    }


    public String css(Attribute attribute, String value)
    {
        switch(this)
        {
            case EQUALS:
                value = String.format("%s='%s'", attribute.toAttributeName(), value);
                break;
            case NOT_EQUALS:
                value = String.format("%s='%s'", attribute.toAttributeName(), value);
                break;
            case CONTAINS:
                value = String.format("%s*='%s'", attribute.toAttributeName(), value);
                break;
            case NOT_CONTAINS:
                value = String.format("%s*='%s'", attribute.toAttributeName(), value);
                break;
            case EXISTS:
                value = String.format("%s", attribute.toAttributeName());
                break;
            case NOT_EXIST:
                value = String.format("%s", attribute.toAttributeName());
                break;
            default:
                throw new IllegalArgumentException();
        }
        return value;
    }

    public String cssFormat(Attribute attribute, String value)
    {

        switch(this)
        {
            case EQUALS:
                value = String.format("[%s]", css(attribute, value));
                break;
            case NOT_EQUALS:
                value = String.format(":not([%s])", css(attribute, value));
                break;
            case CONTAINS:
                value = String.format("[%s]", css(attribute, value));
                break;
            case NOT_CONTAINS:
                value = String.format(":not([%s])", css(attribute, value));
                break;
            case EXISTS:
                value = String.format("[%s]", css(attribute, value));
                break;
            case NOT_EXIST:
                value = String.format(":not([%s])", css(attribute, value));
                break;
            default:
                throw new IllegalArgumentException();
        }
        return value;
    }
}
