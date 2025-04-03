package com.test.trans.client.positive.testcases;

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

public class MyOrders {

    WebDriver driver;
    WebDriverWait wait;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "D:\\Parallax\\TransEx_V2_Automation\\Trans_Test_Automation\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/MyOrdersReport.html");
        spark.config().setDocumentTitle("My Orders Automation Report");
        spark.config().setReportName("View My Orders");
        spark.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(spark);

        test = extent.createTest("My Orders View Test");
    }

    @Test
    public void viewMyOrders() {
        try {
            // ---------- OPEN PAGE ----------
            driver.get("https://release.v2.trans-ex.parallaxtec.dev/landing");
            test.info("Navigated to landing page");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'LOADING') or contains(@class,'loading')]")
            ));

            // ---------- LOGIN ----------
            WebElement clientLoginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Client Login')]")
            ));
            clientLoginBtn.click();

            WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Enter the mail']")
            ));
            WebElement password = driver.findElement(By.xpath("//input[@placeholder='Enter the Password']"));

            email.sendKeys("kelly@gmail.com");
            password.sendKeys("12345678");

            WebElement loginBtn = driver.findElement(By.xpath("//button[@type='submit']"));
            loginBtn.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Welcome Back')]")
            ));
            test.pass("Login successful");

            // ---------- CLICK MY ORDERS ----------
            WebElement myOrdersBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class,'side-menu__title') and text()='My Orders']")
            ));
            myOrdersBtn.click();
            test.pass("Navigated to My Orders");

            // ---------- VERIFY ORDER TILES ----------
            WebElement deliveredTile = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(),'DELIVERED')]/following-sibling::div//span[contains(text(),'120')]")
            ));
            WebElement partiallyDeliveredTile = driver.findElement(By.xpath("//div[contains(text(),'PARTIALLY DELIVERED')]"));
            WebElement returnedTile = driver.findElement(By.xpath("//div[contains(text(),'RETURNED TO CLIENT')]"));
            WebElement receivedTile = driver.findElement(By.xpath("//div[contains(text(),'RECEIVED BY CLIENT')]"));

            Assert.assertTrue(deliveredTile.isDisplayed(), "Delivered tile not visible");
            Assert.assertTrue(partiallyDeliveredTile.isDisplayed(), "Partially Delivered tile not visible");
            Assert.assertTrue(returnedTile.isDisplayed(), "Returned tile not visible");
            Assert.assertTrue(receivedTile.isDisplayed(), "Received tile not visible");

            test.pass("Verified all 4 order status tiles");

            // ---------- VERIFY ORDER TABLE ----------
            WebElement orderTableRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//table//tbody/tr")
            ));
            Assert.assertTrue(orderTableRow.isDisplayed(), "Order rows not found!");
            test.pass("Verified order table has records");

        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            Assert.fail("Exception occurred: " + e.getMessage());
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
