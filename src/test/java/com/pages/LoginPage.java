package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    By email = By.id("Email");
    By password = By.name("Password");
    By loginbtn = By.xpath("//input[@value='Log in']");

    public WebElement enteremail(){
        return driver.findElement(email);
    }
    public WebElement enterpassword(){
        return driver.findElement(password);
    }
    public WebElement clicklogin()
    {
        return driver.findElement(loginbtn);
    }
}
