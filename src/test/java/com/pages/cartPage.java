package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class cartPage {
    WebDriver driver;

    public cartPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(id = "termsofservice")
    private WebElement checkbox;

    public WebElement getCheckbox(){
        return checkbox;
    }

    @FindBy(name = "checkout")
    private WebElement checkoutBtn;

    public WebElement getCheckoutBtn(){
        return checkoutBtn;
    }
    public void acceptTerms() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(checkbox));
        checkbox.click();
    }

    public void clickCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn));
        checkoutBtn.click();
    }

    private By con=By.xpath("//input[@onclick=\"Billing.save()\"]");
    private By con2=By.xpath("//input[@onclick=\"Shipping.save()\"]");
    private By con3=By.xpath("//input[@class='button-1 shipping-method-next-step-button']");
    private By con4=By.xpath("//input[@class='button-1 payment-method-next-step-button']");
    private By con5=By.xpath("//input[@class='button-1 payment-info-next-step-button']");
    private By confirm=By.xpath("//input[@value='Confirm']");

    public WebElement clickcon(){
        return driver.findElement(con);
    }
    public WebElement clickcon2(){
        return driver.findElement(con2);
    }
    public WebElement clickcon3(){
        return driver.findElement(con3);
    }
    public WebElement clickcon4(){
        return driver.findElement(con4);
    }
    public WebElement clickcon5(){
        return driver.findElement(con5);
    }
    public WebElement clickconfirm(){
        return driver.findElement(confirm);
    }
}
