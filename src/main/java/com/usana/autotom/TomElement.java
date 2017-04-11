package com.usana.autotom;

import org.openqa.selenium.WebDriver;

import java.util.UUID;

/**
 * Created by kerrysouthworth on 1/3/17.
 */
public interface TomElement
{
    String getName();

    String getUuid();

    Long getId(); // used for sorting TOM elements

    void execute(WebDriver webDriver);
}
