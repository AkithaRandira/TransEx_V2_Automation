package com.test.trans.client.negative.testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class IncompatiblePassword {

    WebDriver driver;
    WebDriverWait wait;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "D:\\Parallax\\TransEx_V2_Automation\\Trans_Test_Automation\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/IncompatiblePasswordReport.html");
        spark.config().setDocumentTitle("Negative Login Report");
        spark.config().setReportName("Incompatible Password Test");
        spark.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(spark);

        test = extent.createTest("Login with Incompatible Password");
    }

    @Test
    public void testLoginWithWrongPassword() {
        try {
            driver.get("https://release.v2.trans-ex.parallaxtec.dev/landing");
            test.info("Opened landing page");

            WebElement clientLoginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[contains(text(),'Client Login')]")
            ));
            clientLoginBtn.click();
            test.pass("Clicked Client Login");

            WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Enter the mail']")
            ));
            WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Enter the Password']")
            ));
            test.pass("Login form loaded");

            username.sendKeys("kelly@gmail.com");  // valid user
            password.sendKeys("wrongPassword123"); // incorrect password
            test.pass("Entered valid email with incorrect password");

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@type='submit']")
            ));
            loginButton.click();
            test.pass("Clicked login button");

            // Validate error message or toast
            WebElement errorToast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Invalid') or contains(text(),'incorrect') or contains(text(),'failed')]")
            ));
            Assert.assertTrue(errorToast.isDisplayed(), "Expected error message not displayed!");
            test.pass("Error displayed correctly: " + errorToast.getText());

        } catch (Exception e) {
            test.fail("Test failed due to exception: " + e.getMessage());
            Assert.fail("Test failed due to exception", e);
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }
}
