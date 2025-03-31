package com.test.trans.client.negative.testcases;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class SignUpWithInvalidData {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "D:\\Parallax\\TransEx_V2_Automation\\Trans_Test_Automation\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/SignUpWithInvalidDataReport.html");
        spark.config().setDocumentTitle("Negative Signup Report");
        spark.config().setReportName("Invalid Login Attempt Validation");
        spark.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(spark);
        test = extent.createTest("SignUp/Login with Invalid Credentials");
    }

    @Test
    public void loginWithInvalidCredentials() {
        try {
            driver.get("https://release.v2.trans-ex.parallaxtec.dev/landing");
            test.info("Opened landing page");

            // Click Client Login
            WebElement clientLoginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Client Login')]")
            ));
            clientLoginBtn.click();
            test.pass("Clicked on 'Client Login'");

            // Fill invalid credentials
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Enter the mail']")
            ));
            WebElement passwordField = driver.findElement(
                    By.xpath("//input[@placeholder='Enter the Password']")
            );
            emailField.sendKeys("invaliduser@test.com");
            passwordField.sendKeys("wrongpass123");
            test.pass("Entered invalid email and password");

            // Click Login
            WebElement loginBtn = driver.findElement(By.xpath("//button[@type='submit']"));
            loginBtn.click();
            test.info("Clicked login button");

            // Wait and validate error message/toast
            WebElement errorToast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Invalid') or contains(text(),'incorrect') or contains(text(),'failed') or contains(text(),'doesn’t exist') or contains(text(),'not match')]")
            ));

            Assert.assertTrue(errorToast.isDisplayed(), "Error message not shown!");
            test.pass("Error message displayed: " + errorToast.getText());

        } catch (Exception e) {
            String screenshotPath = takeScreenshot("SignUpInvalidDataError");
            test.fail("Test failed: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            Assert.fail("Test failed due to exception", e);
        }
    }

    public String takeScreenshot(String fileName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = "test-output/screenshots/" + fileName + ".png";
            File dest = new File(path);
            dest.getParentFile().mkdirs(); // ensure directory exists
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
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
