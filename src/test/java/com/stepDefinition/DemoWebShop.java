package com.stepDefinition;

import io.cucumber.java.en.*;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

public class DemoWebShop{
    WebDriver driver;

    @Given("I launch the application")
    public void launchApplication() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://demowebshop.tricentis.com/");

        String title = driver.getTitle();
        Assert.assertTrue("Title does not match", title.contains("Demo Web Shop"));
    }


    @When("I login with email and password")
    public void loginIntoApplication() throws IOException {
        FileInputStream fis = new FileInputStream(
                "Excel_files/DWS_Credentials.xlsx");

        Workbook wb = new XSSFWorkbook(fis);
        Sheet sheet = wb.getSheet("Sheet1");
        DataFormatter formatter = new DataFormatter();

        String email = formatter.formatCellValue(sheet.getRow(1).getCell(0));
        String password = formatter.formatCellValue(sheet.getRow(1).getCell(1));
        wb.close();

        driver.findElement(By.linkText("Log in")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));

        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.cssSelector("input.login-button")).click();

        boolean isLogoutVisible = driver.findElement(By.linkText("Log out")).isDisplayed();
        Assert.assertTrue("Logout button not displayed", isLogoutVisible);
    }

    @And("I search for {string} and select a product")
    public void searchProduct(String productName) {
        driver.findElement(By.id("small-searchterms")).sendKeys(productName);
        driver.findElement(By.cssSelector("input.search-box-button")).click();

        // Click on the first product title (e.g., 14.1-inch Laptop)
        WebElement productLink = driver.findElement(By.cssSelector(".product-title a"));
        String expectedProductName = productLink.getText();
        productLink.click();

        String displayedProductName = driver.findElement(By.cssSelector(".product-name")).getText();
        Assert.assertEquals(expectedProductName, displayedProductName);
    }

    @And("I add the product to cart and complete checkout")
    public void addToCartAndCheckout() {
        driver.findElement(By.cssSelector("input.add-to-cart-button")).click();
        driver.findElement(By.linkText("Shopping cart")).click();

        // Agree to terms and checkout
        driver.findElement(By.id("termsofservice")).click();
        driver.findElement(By.id("checkout")).click();

        // Checkout Process (Billing, Shipping, Method, Payment, Confirm)
        // Note: Using 'Continue' buttons sequentially
        driver.findElement(By.xpath("//input[@onclick='Billing.save()']")).click();
        driver.findElement(By.xpath("//input[@onclick='Shipping.save()']")).click();
        driver.findElement(By.xpath("//input[@onclick='ShippingMethod.save()']")).click();
        driver.findElement(By.xpath("//input[@onclick='PaymentMethod.save()']")).click();
        driver.findElement(By.xpath("//input[@onclick='PaymentInfo.save()']")).click();
        driver.findElement(By.xpath("//input[@onclick='ConfirmOrder.save()']")).click();

        String successMsg = driver.findElement(By.xpath("//strong[normalize-space()='Your order has been successfully processed!']")).getText();
        Assert.assertEquals("Your order has been successfully processed!", successMsg);
    }

    @Then("I logout from the application")
    public void logoutFromApplication() {
        driver.findElement(By.linkText("Log out")).click();
        boolean isLoginVisible = driver.findElement(By.linkText("Log in")).isDisplayed();
        Assert.assertTrue("Login button not visible after logout", isLoginVisible);
        driver.quit();
    }
}
