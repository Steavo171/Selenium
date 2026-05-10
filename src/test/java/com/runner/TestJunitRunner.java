package com.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "Features/DemoWebShopEtoE.feature",glue = "com.stepDefinition",dryRun = false
        ,monochrome = true,plugin = {"html: target/report.html" , "pretty"},tags="@DemoWebShop")    //,tags = "@Smoke"  ,tags="@MultipleData"

public class TestJunitRunner {
}
