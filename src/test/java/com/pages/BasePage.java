package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {
    WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver=driver;
    }

    private By login_link = By.linkText("Log in");
    private By register_link = By.linkText("Register");

    public WebElement loginlink(){
        return driver.findElement(login_link);
    }
    public WebElement registerlink(){
        return driver.findElement(register_link);
    }
}
