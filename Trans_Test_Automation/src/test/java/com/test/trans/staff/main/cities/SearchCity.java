package com.test.trans.staff.main.cities;

import com.test.trans.staff.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class SearchCity extends BaseTest {

    @Test(priority = 1)
    public void createCityTest() {
        // Call the login function from BaseTest
        login();
    }
}