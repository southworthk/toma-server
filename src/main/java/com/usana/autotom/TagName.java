package com.usana.autotom;


public enum TagName
{
    ANY,
    A,
    ADDRESS,
    ANIMATE,
    BODY,
    BUTTON,
    CHECKBOX,
    DIV,
    EM,
    EMBED,
    FONT,
    FOOTER,
    FORM,
    FRAME,
    G,
    H1,
    H2,
    H3,
    H4,
    HEADER,
    HTML,
    I,
    IFRAME,
    IMG,
    INPUT,
    INPUTBUTTON,
    LABEL,
    LI,
    LINK,
    META,
    NAV,
    NUMBERFIELD,
    OL,
    OPTION,
    P,
    PRE,
    PASSWORD,
    PWP_ITEM,
    RADIOBUTTON,
    SECTION,
    SELECT,
    SPAN,
    STRONG,
    SUBMIT,
    SVG,
    TABLE,
    TBODY,
    THEAD,
    TEXTFIELD,
    TEXTAREA,
    TITLE,
    THA_SELECT,
    TH,             // Table header Cell
    TR,             // Table Row
    TD,             // Table data cell
    TSPAN,
    UL;

    /**
     * Method to create XPath segment from the tag name
     *
     * @return XPath segment
     */
    public String toXpathFormat()
    {
        String element;
        switch (this)
        {
            case ANY:
                element = "*";
                break;
            case CHECKBOX:
                element = "input[@type='checkbox']";
                break;
            case INPUTBUTTON:
                element = "input[@type='button']";
                break;
            case NUMBERFIELD:
                element = "input[@type='number']";
                break;
            case RADIOBUTTON:
                element = "input[@type='radio']";
                break;
            case TEXTFIELD:
                element = "input[@type='text']";
                break;
            case PASSWORD:
                element = "input[@type='password']";
                break;
            case SUBMIT:
                element = "input[@type='submit']";
                break;
            default:
                element = this.toString().replace("_", "-").toLowerCase();
        }
        return element;
    }

    /**
     * Method to create CSS segment from the tag name
     *
     * @return CSS segment
     */
    public String toCssFormat()
    {
        String element;
        switch (this)
        {
            case ANY:
                element = "*";
                break;
            case CHECKBOX:
                element = "input[type='checkbox']";
                break;
            case INPUTBUTTON:
                element = "input[type='button']";
                break;
            case NUMBERFIELD:
                element = "input[type='number']";
                break;
            case RADIOBUTTON:
                element = "input[type='radio']";
                break;
            case TEXTFIELD:
                element = "input[type='text']";
                break;
            case PASSWORD:
                element = "input[type='password']";
                break;
            case SUBMIT:
                element = "input[type='submit']";
                break;
            default:
                element = this.toString().replace("_", "-").toLowerCase();
        }
        return element;
    }
}
