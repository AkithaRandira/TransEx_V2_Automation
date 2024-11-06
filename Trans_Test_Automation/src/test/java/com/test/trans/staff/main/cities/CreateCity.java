package com.test.trans.staff.main.cities;

import com.test.trans.staff.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class CreateCity extends BaseTest {

    @Test(priority = 1)
    public void createCityTest() {
        // Call the login function from BaseTest
        login();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll down to find the "Cities" option in the sidebar and click it
        WebElement citiesMenuOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div/div[3]/nav/ul/li[24]/a/div[2]")));
        js.executeScript("arguments[0].scrollIntoView(true);", citiesMenuOption);
        citiesMenuOption.click();

        // Wait until the "Add New City" button is clickable and click it
        WebElement addNewCityButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div/div[3]/div/div/div[1]/div/a")));
        addNewCityButton.click();

        // Additional steps to fill out the form and create a city can go here
        System.out.println("Create City function executed.");
    }
}
