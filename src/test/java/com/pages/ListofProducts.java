package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ListofProducts {
    WebDriver driver;

    public ListofProducts(WebDriver driver){
        this.driver = driver;
    }

    By sortby = By.id("products-orderby");
    By firstproduct = By.partialLinkText("Build your own expensive computer");
    public WebElement sortbyDropdown(){
        return driver.findElement(sortby);
    }
    public void clickonfirstproduct(){
        driver.findElement(firstproduct).click();
    }

    By addtocartbtn = By.xpath("//input[@id='add-to-cart-button-74']");

    public WebElement addtocart(){
        return driver.findElement(addtocartbtn);
    }
}
