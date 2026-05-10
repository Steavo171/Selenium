package com.Excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class DWSEXCEL extends com.Excel.BaseClassDWSEXCEL {

    int currentRow = 1;

    @Test(dataProvider = "DWSData")     //testng
    public void masterTest(String username, String password) throws Exception {
        String orderId = "N/A";
        try {
            launchApplication();
            logIntoApplication(username, password);
            searchProduct();

            orderId = addToCartAndCheckOut();

            logoutFromApplication();

            // Write both Status and Order ID on Success
            writeResultsToExcel(currentRow, "Passed", orderId);
        } catch (Exception e) {
            // Write Failure status
            writeResultsToExcel(currentRow, "Failed", orderId);
            throw e;
        } finally {
            currentRow++;
        }
    }

    public void writeResultsToExcel(int rowNum, String status, String orderId) throws IOException {
        String path = "Excel_files/DWSData.xlsx";
        FileInputStream fis = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet("Sheet1");
        Row row = sheet.getRow(rowNum);


        Cell statusCell = row.createCell(5);

        statusCell.setCellValue(status);


        Cell idCell = row.createCell(6);
        idCell.setCellValue(orderId);

        FileOutputStream output = new FileOutputStream(path);
        workbook.write(output);
        workbook.close();
        fis.close();
    }


    public void launchApplication() {
        driver.get("https://demowebshop.tricentis.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        String title = driver.getTitle();
        Assert.assertTrue(title.equals("Demo Web Shop"));

    }



    public void logIntoApplication(String username,String password) {
        driver.findElement(By.linkText("Log in")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
        driver.findElement(By.id("Email")).sendKeys(username);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@class='button-1 login-button']")).click();
        WebElement logout = driver.findElement(By.linkText("Log out"));
        Assert.assertTrue(logout.isDisplayed());

    }

    public void searchProduct() {
        driver.findElement(By.xpath("//input[@id='small-searchterms']")).sendKeys("laptop");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        driver.findElement(By.linkText("14.1-inch Laptop")).click();
        Assert.assertTrue(driver.getTitle().contains("14.1-inch Laptop"));
        WebElement productName = driver.findElement(By.xpath("//h1[normalize-space()='14.1-inch Laptop']"));
        Assert.assertTrue(productName.isDisplayed());

    }

    public String addToCartAndCheckOut() {
        driver.findElement(By.id("add-to-cart-button-31")).click();
        driver.findElement(By.xpath("//span[normalize-space()='Shopping cart']")).click();
        driver.findElement(By.id("termsofservice")).click();
        driver.findElement(By.id("checkout")).click();
        WebElement country = driver.findElement(By.id("BillingNewAddress_CountryId"));
        Select s = new Select(country);
        List<WebElement> address = driver.findElements(By.id("billing-address-select"));

        if(!address.isEmpty()) {
            Select s1 = new Select(address.get(0));
            s1.selectByVisibleText("New Address");

            s.selectByVisibleText("India");
            driver.findElement(By.id("BillingNewAddress_City")).sendKeys("Noida");
            driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys("Noida");
            driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys("201304");
            driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys("9876543210");
        }
        else
        {
            s.selectByVisibleText("India");
            driver.findElement(By.id("BillingNewAddress_City")).sendKeys("Noida");
            driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys("Noida");
            driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys("201304");
            driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys("9876543210");
        }
        driver.findElement(By.cssSelector("#billing-buttons-container > input.button-1")).click();
        driver.findElement(By.cssSelector("#shipping-buttons-container > input.button-1")).click();
        driver.findElement(By.xpath("//input[@id='shippingoption_1']")).click();
        driver.findElement(By.cssSelector(".button-1.shipping-method-next-step-button")).click();
        driver.findElement(By.id("paymentmethod_1")).click();
        driver.findElement(By.cssSelector(".button-1.payment-method-next-step-button")).click();
        driver.findElement(By.cssSelector(".button-1.payment-info-next-step-button")).click();
        driver.findElement(By.cssSelector(".button-1.confirm-order-next-step-button")).click();
        String order = driver.findElement(By.xpath("//strong[normalize-space()='Your order has been successfully processed!']")).getText();
        Assert.assertTrue(order.equals("Your order has been successfully processed!"));
        String rawDetails = driver.findElement(By.cssSelector("div.section > ul.details > li")).getText();

        // Extract only digits: "1234567"
        String cleanId = rawDetails.replaceAll("[^0-9]", "");

        driver.findElement(By.xpath("//input[@type='button']")).click(); // Click Continue
        return cleanId;



    }


    public void logoutFromApplication()
    {
        driver.findElement(By.linkText("Log out")).click();
        WebElement login  = driver.findElement(By.linkText("Log in"));
        Assert.assertTrue(login.isDisplayed());
    }

    @DataProvider(name = "DWSData")
    public Object[][] getExcelData() throws IOException {
        FileInputStream fis = new FileInputStream("Excel_files/DWSData.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet("Sheet1");


        DataFormatter formatter = new DataFormatter();

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount - 1][colCount];

        for (int i = 1; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                data[i - 1][j] = formatter.formatCellValue(sheet.getRow(i).getCell(j));
            }
        }
        workbook.close();
        return data;
    }
}//done
























































































































































































