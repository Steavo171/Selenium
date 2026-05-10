package com.DWS;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;

public class DWSTest extends BaseClass {
    @Test(priority = 1)
    public void launchApplication() {

        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Demo Web Shop"));
//        System.out.println(title);
    }

    @Test(priority = 2)
    @Parameters({"useremail","password"})
    public void loginIntoApplication() {

        driver.findElement(By.linkText("Log in")).click();
        driver.findElement(By.id("Email")).sendKeys("maxplank@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("1234567890");
        driver.findElement(By.xpath("//input[@value='Log in']")).click();

        Assert.assertTrue(driver.findElement(By.linkText("Log out")).isDisplayed());
    }

    @Test(priority = 3)
    public void searchProduct() {

        driver.findElement(By.id("small-searchterms")).sendKeys("laptop");
        driver.findElement(By.xpath("//input[@value='Search']")).click();
        driver.findElement(By.linkText("14.1-inch Laptop")).click();

        Assert.assertTrue(driver.findElement(By.cssSelector("div.product-name h1")).isDisplayed());
    }

    @Test(priority = 4)
    public void addToCartAndCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.findElement(By.id("add-to-cart-button-31")).click();
        driver.findElement(By.linkText("Shopping cart")).click();
        driver.findElement(By.id("termsofservice")).click();
        driver.findElement(By.id("checkout")).click();

        WebElement addressDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("billing-address-select")));

        Select addressSelect = new Select(addressDropdown);
        addressSelect.selectByVisibleText("New Address");

        WebElement countryDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("BillingNewAddress_CountryId")));

        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText("India");

        driver.findElement(By.xpath("//input[@id='BillingNewAddress_City']")).sendKeys("Delhi");
        driver.findElement(By.xpath("//input[@id='BillingNewAddress_Address1']")).sendKeys("Delhi");
        ;
        driver.findElement(By.xpath("//input[@id='BillingNewAddress_ZipPostalCode']")).sendKeys("112233");
        driver.findElement(By.xpath("//input[@id='BillingNewAddress_PhoneNumber']")).sendKeys("9876543210");


        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@onclick='Billing.save()']"))).click();

        driver.findElement(By.xpath("//input[@onclick='Shipping.save()']")).click();
        driver.findElement(By.xpath("//input[@onclick='ShippingMethod.save()']")).click();
        driver.findElement(By.xpath("//input[@onclick='PaymentMethod.save()']")).click();
        driver.findElement(By.xpath("//input[@onclick='PaymentInfo.save()']")).click();
        driver.findElement(By.xpath("//input[@onclick='ConfirmOrder.save()']")).click();

//        Assert.assertTrue(driver.getPageSource().contains("Your order has been successfully processed!"));

        wait.until(ExpectedConditions.urlContains("checkout/completed"));

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout/completed"));

    }

    @Test(priority = 5, dependsOnMethods = "loginIntoApplication")
    public void logoutFromApplication() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log out"))).click();
        } catch (StaleElementReferenceException e) {
            driver.findElement(By.linkText("Log out")).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Log in")));
        Assert.assertTrue(driver.findElement(By.linkText("Log in")).isDisplayed());

    }
}