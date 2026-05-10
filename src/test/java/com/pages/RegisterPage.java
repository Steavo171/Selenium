package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {
    WebDriver driver;

    public RegisterPage(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(id="gender-male")
    private WebElement gender;
    @FindBy(id="FirstName")
    private WebElement fname;
    @FindBy(id="LastName")
    private WebElement lname;
    @FindBy(id="Email")
    private WebElement email;
    @FindBy(id="Password")
    private WebElement password;
    @FindBy(name = "ConfirmPassword")
    private WebElement cpassword;
    @FindBy(id="register-button")
    private WebElement registerbtn;

    public WebElement getGender(){
        return gender;
    }
    public void getFname(String fn){
        fname.sendKeys(fn);
    }
    public void getLname(String ln){
        lname.sendKeys(ln);
    }
    public void getEmail(String email){
        this.email.sendKeys(email);
    }
    public void getPassword(String password){
        this.password.sendKeys(password);
    }

    public void getCpassword(String confirmpassword){
        cpassword.sendKeys(confirmpassword);
    }

    public WebElement getRegisterbtn(){
        return registerbtn;
    }

    public void Registration(){
        getGender().click();
        getFname("John");
        getLname("Doe");

    }
}
