package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver){
        this.driver= driver;
    }

    By logout_link = By.linkText("Log out");
    public WebElement logOut(){
        return driver.findElement(logout_link);
    }

    By computers = By.linkText("COMPUTERS");
    public WebElement computerclick() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(computers));
        return element;
    }

    By desktop = By.xpath("//a[normalize-space()='Desktops']");
    public WebElement desktopEle(){
        return driver.findElement(desktop);
    }


}
