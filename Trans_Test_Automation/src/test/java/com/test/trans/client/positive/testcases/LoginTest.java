package com.test.trans.client.positive.testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        // Setup WebDriver
        System.setProperty("webdriver.chrome.driver", "D:\\Parallax\\TransEx_V2_Automation\\Trans_Test_Automation\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Setup Extent Report
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/LoginTestReport.html");
        spark.config().setDocumentTitle("Automation Report");
        spark.config().setReportName("Client Login Test");
        spark.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(spark);

        test = extent.createTest("Client Login Test");
    }

    @Test
    public void testClientLogin() {
        try {
            driver.get("https://release.v2.trans-ex.parallaxtec.dev/landing");
            test.info("Opened landing page");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement clientLoginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[contains(text(),'Client Login')]")
            ));
            test.pass("Client Login button visible");
            clientLoginBtn.click();

            WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Enter the mail']")
            ));
            WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Enter the Password']")
            ));
            test.pass("Login form loaded");

            username.sendKeys("kelly@gmail.com");
            password.sendKeys("12345678");
            test.pass("Entered login credentials");

// Wait for 2 seconds before clicking login
            Thread.sleep(2000);


            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@type='submit']")
            ));
            loginButton.click();
            test.pass("Clicked login button");

            WebElement welcomeText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Welcome Back')]")
            ));
            Assert.assertTrue(welcomeText.isDisplayed(), "Login failed or dashboard not visible!");
            test.pass("Dashboard loaded - login successful");

        } catch (Exception e) {
            test.fail("Test failed with exception: " + e.getMessage());
            Assert.fail("Test failed due to exception", e);
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush(); // Write the report
    }
}
