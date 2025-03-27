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

public class AddNewSingleOrder {

    WebDriver driver;
    WebDriverWait wait;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "D:\\Parallax\\TransEx_V2_Automation\\Trans_Test_Automation\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies(); // Start fresh
        wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Increased timeout

        // Setup report
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/AddNewSingleOrderReport.html");
        spark.config().setDocumentTitle("Add Order Automation Report");
        spark.config().setReportName("Add Single Order");
        spark.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(spark);

        test = extent.createTest("Add New Single Order");
    }

    @Test
    public void addOrderTest() {
        try {
            // ---------- OPEN PAGE ----------
            driver.get("https://release.v2.trans-ex.parallaxtec.dev/landing");
            test.info("Navigated to landing page");

            // ✅ Wait for loading screen to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'LOADING') or contains(@class,'loading')]")
            ));

            // ---------- LOGIN ----------
            WebElement clientLoginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Client Login')]")
            ));
            clientLoginBtn.click();
            test.pass("Clicked Client Login");

            WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Enter the mail']")
            ));
            WebElement password = driver.findElement(By.xpath("//input[@placeholder='Enter the Password']"));

            email.sendKeys("kelly@gmail.com");
            password.sendKeys("12345678");
            test.pass("Entered credentials");

            WebElement loginBtn = driver.findElement(By.xpath("//button[@type='submit']"));
            loginBtn.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Welcome Back')]")
            ));
            test.pass("Login successful");

            // ---------- ADD NEW ORDER ----------
            WebElement addOrderBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='side-menu__title' and text()='Add New Order']")
            ));
            addOrderBtn.click();
            test.pass("Clicked 'Add New Order'");

            // ✅ Generate random 8-digit waybill
            int min = 10000000;
            int max = 99999999;
            String waybillNumber = String.valueOf((int)(Math.random() * (max - min + 1) + min));

            WebElement waybill = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Enter the waybill number here']")
            ));
            waybill.sendKeys(waybillNumber);
            test.info("Entered random 8-digit waybill: " + waybillNumber);

            driver.findElement(By.xpath("//input[@placeholder='Enter your order number']")).sendKeys("1");
            driver.findElement(By.xpath("//input[@placeholder='Enter customer name']")).sendKeys("Test Customer");
            driver.findElement(By.xpath("//input[@placeholder='Enter address']")).sendKeys("Colombo");
            driver.findElement(By.xpath("//input[@placeholder='Enter first phone number']")).sendKeys("0701825283");
            driver.findElement(By.xpath("//input[@placeholder='Enter second phone number']")).sendKeys("0382244448");
            driver.findElement(By.xpath("//textarea[@placeholder='Enter order description']")).sendKeys("Test order 01");

            // City Dropdown
            WebElement cityDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@type='text' and @readonly]")
            ));
            cityDropdown.click();

            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Search...']")
            ));
            searchInput.sendKeys("Colombo");
            Thread.sleep(1000); // allow options to appear

            WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//label[contains(text(),'Colombo 01')]")
            ));
            cityOption.click();

            driver.findElement(By.xpath("//input[@placeholder='Enter COD amount']")).sendKeys("6000");
            driver.findElement(By.xpath("//textarea[@placeholder='Enter remarks']")).sendKeys("Test remarks for order 01");

            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Place Order')]")
            ));
            submitBtn.click();
            test.pass("Submitted the order");

            // ✅ Confirm success popup
            WebElement successPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Created successfully')]")
            ));
            Assert.assertTrue(successPopup.isDisplayed(), "Success popup not visible!");
            test.pass("Success confirmation popup displayed");

        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
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
