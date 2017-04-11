package com.usana.autotom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;

/**
 * Created by kerrysouthworth on 3/27/17.
 */
public class ExecutionThread implements Runnable
{
    ArrayList<TomElement> testableElements;

    public void run(){
        WebDriver driver = new FirefoxDriver();
        String customerId = Thread.currentThread().getName();
        System.out.println("thread running: "+Thread.currentThread().getName());
        for(TomElement tomElement : testableElements){
            if(tomElement instanceof ValidInput)
            {
                ValidInput validInput = (ValidInput) tomElement;
                if(validInput.getType().equals("Customer"))
                {
                    validInput.execute(driver,customerId);
                }
                else
                {
                    tomElement.execute(driver);
                }
            }
            else
            {
                tomElement.execute(driver);
            }
            // TODO: write test tests to Firebase
            // see http://pscode.rs/firebase-and-spring-boot-integration/
            // github repository is here: https://github.com/savicprvoslav/Spring-Boot-starter
        }
        driver.close();
    }

    public ArrayList<TomElement> getTestableElements()
    {
        return testableElements;
    }

    public void setTestableElements(ArrayList<TomElement> testableElements)
    {
        this.testableElements = testableElements;
    }
}
