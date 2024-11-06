package com.test.trans.staff.positive.testcases;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class ValidStaffLoginTrans {
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "D:\\Parallax\\TransEx_V2_Automation\\Trans_Test_Automation\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://ui.v2.transexpress.parallaxtec.dev/");
    }

    @Test(priority = 1)
    public void transStaffValidLogin() throws InterruptedException {
        // Scroll down to reveal the staff login button if needed
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int steps = 3;
        long pauseTime = 100;
        int scrollAmount = 50;

        for (int i = 0; i < steps; i++) {
            js.executeScript("window.scrollBy(0, " + scrollAmount + ");");
            Thread.sleep(pauseTime);
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click on Staff Login button
        WebElement staffLoginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div/div/div[1]/div/div[4]/a/button")));
        staffLoginBtn.click();

        // Verify form title
        WebElement formElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
        String actualFormTitle = formElement.getText();
        String expectedFormTitle = "Welcome Back";
        Assert.assertEquals(actualFormTitle, expectedFormTitle, "Form title does not match expected title");

        System.out.println("Expected title of Login Page: " + expectedFormTitle);

        // Enter email
        WebElement staffEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div/form/div[1]/input")));
        staffEmail.sendKeys("staff1@gmail.com");

        // Enter password
        WebElement staffPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div/form/div[1]/div/input")));
        staffPassword.sendKeys("12345678");

        // Click login button
        WebElement stLoginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div/form/div[3]/button")));
        stLoginBtn.click();

        // Verify that the staff dashboard has been reached
        WebElement dashboardLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/div/div[3]/div/div/div[1]/div/div[1]/div[1]/h2"))); // Use a unique locator from the dashboard page
        Assert.assertTrue(dashboardLabel.isDisplayed(), "Failed to reach the staff dashboard.");
    }

    @AfterClass
    public void browserQuit() {
        if (driver != null) {
            driver.quit();
        }
    }
}
