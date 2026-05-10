package com.testScript;
import com.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class EtoEScenario{
    WebDriver driver;

    BasePage b;
    LoginPage lp;
    HomePage hp;
    ListofProducts product;
    cartPage cp;
    @BeforeClass
    public void setup(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demowebshop.tricentis.com");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }
    @Test(priority = 1)
    public void logindemo(){

        b = new BasePage(driver);
        b.loginlink().click();

        lp= new LoginPage(driver);
        lp.enteremail().sendKeys("maxplank@gmail.com");
        lp.enterpassword().sendKeys("1234567890");
        lp.clicklogin().click();

        hp = new HomePage(driver);
        Assert.assertTrue(hp.logOut().isDisplayed());
//        hp.logOut().click();
    }

    @Test(priority = 2)
    public void MousehoveronComputer_and_addtocart(){
        hp=new HomePage(driver);
        Actions a = new Actions(driver);
        a.moveToElement(hp.computerclick()).build().perform();
        hp.desktopEle().click();

        product = new ListofProducts(driver);
        Select s = new Select(product.sortbyDropdown());
        s.selectByVisibleText("Price: High to Low");

        product.clickonfirstproduct();
        product.addtocart().click();
    }

    @Test(priority = 3)
    public void checkoutTest(){

        driver.findElement(By.xpath("//a[@class='ico-cart']")).click();
        cp = new cartPage(driver);

        cp.acceptTerms();
        cp.clickCheckout();

        cp.clickcon().click();
        cp.clickcon2().click();
        cp.clickcon3().click();
        cp.clickcon4().click();
        cp.clickcon5().click();
        cp.clickconfirm().click();
    }
    @AfterClass
    public void tearDown(){
//        driver.quit();
    }
}
