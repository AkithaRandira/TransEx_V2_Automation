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

public class ForgotPasswordWithInvalidMail {

    WebDriver driver;
    WebDriverWait wait;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "D:\\Parallax\\TransEx_V2_Automation\\Trans_Test_Automation\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ForgotPasswordInvalidMailReport.html");
        spark.config().setDocumentTitle("Negative Test Report");
        spark.config().setReportName("Forgot Password - Invalid Mail");
        spark.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(spark);

        test = extent.createTest("Forgot Password with Invalid Email");
    }

    @Test
    public void forgotPasswordInvalidEmailTest() {
        try {
            driver.get("https://release.v2.trans-ex.parallaxtec.dev/landing");
            test.info("Navigated to login page");

            // Click on Client Login
            WebElement clientLoginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Client Login')]")
            ));
            clientLoginBtn.click();
            test.pass("Clicked 'Client Login'");

            // Click on Forgot Password
            WebElement forgotPasswordBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Forgot Password?')]")
            ));
            forgotPasswordBtn.click();
            test.pass("Clicked 'Forgot Password?'");

            // Wait for Reset Password modal
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Enter your email address']")
            ));
            emailField.sendKeys("invaliduser@test.com");
            test.pass("Entered invalid email address");

            WebElement resetBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'SEND PASSWORD RESET LINK')]")
            ));
            resetBtn.click();
            test.pass("Clicked 'Send Password Reset Link'");

            // Check for toast/error message
            WebElement toastMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'not found') or contains(text(),'Invalid') or contains(text(),'failed')]")
            ));

            Assert.assertTrue(toastMsg.isDisplayed(), "Error toast not displayed as expected.");
            test.pass("Validation Passed: Error message displayed - " + toastMsg.getText());

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
