package com.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "Features/DemoWebShopEtoE.feature",glue = "com.stepDefinition",dryRun = false
        ,monochrome = true,plugin = {"html: target/report.html" , "pretty"})
public class TestNGRunner extends AbstractTestNGCucumberTests {

}
