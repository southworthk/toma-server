package com.usana.autotom;

/**
 * Enum to enumerate Attribute
 */
public enum Attribute
{
    ACTION,
    ALIGN,
    ALT,
    ANIMATE,
    ANY,
    ARIA_DESCRIBEDBY,
    ARIA_EXPANDED,
    ARIA_LABEL,
    ARIA_LABELLEDBY,
    BACKGROUND,
    ID,
    CALENDAR_ADMIN_PAGE,
    CHECKED,
    CLASS_NAME,
    COLSPAN,
    CONTAINER_ID,
    CONTENT,
    DATA,
    DATA_ETHNICITY,
    DATA_DISMISS,
    DATA_HASQTIP,
    DATA_HIDE,
    DATA_LANG,
    DATA_LANGUAGE,
    DATA_MENU_ITEM_ID,
    DATA_MENU_ITEM_VISIBLE,
    DATA_NG_CLICK,
    DATA_NG_CONTROLLER,
    DATA_NG_REPEAT,
    DATA_PAGE_ID,
    DATA_PWP_COMPONENT_HASH,
    DATA_PWP_GROUP_LIST_ORDER,
    DATA_PWP_ID,
    DATA_PWP_ID_ELEM,
    DATA_PWP_OPTION_USED,
    DATA_REPORT,
    DATA_SUB_MENU,
    DATA_SUB_MENU_PARENT,
    DATA_TARGET,
    DATA_TEMPLATE_ID,
    DATA_UI_SREF,
    DISABLED,
    DY,
    FOR,
    HEADING,
    HEIGHT,
    HREF,
    LABEL,
    MAXLENGTH,
    MENU_LOC,
    MODAL_BACKDROP,
    NAME,
    NG_BIND_HTML,
    NG_CLASS,
    NG_CLICK,
    NG_IF,
    NG_INCLUDE,
    NG_MODEL,
    NG_REPEAT,
    NG_SHOW,
    NG_STYLE,
    NG_SWITCH_WHEN,
    ONCLICK,
    OPTIONS,
    PLACEHOLDER,
    UIB_POPOVER,
    POPOVER,
    POPOVER_TITLE,
    PWP_ID,
    RANK,
    ROLE,
    TYPE,
    SIZE,
    SRC,
    STATUS,
    STYLE,
    TARGET,
    TEXT_CONTENT,
    TITLE,
    TR,
    UI_VIEW,
    UIB_MODAL_WINDOW,
    USER_INPUT,
    VALUE,
    XMLNS;

    /**
     * Method to return the attribute name string from the enum
     *
     * @return Attribute name string
     */
    public String toAttributeName()
    {
        String value;
        switch (this)
        {
            case ANY:
                value = ".";
                break;
            case CLASS_NAME:
                value = "class";
                break;
            case TEXT_CONTENT:
                value = "textContent";
                break;
            default:
                value = super.toString().replace("_CONTAINS", "").replace("_", "-").toLowerCase();
        }
        return value;
    }
}
