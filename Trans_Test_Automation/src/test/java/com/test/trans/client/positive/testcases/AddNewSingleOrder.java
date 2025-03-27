package com.test.trans.client.positive.testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

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
            // ---------- LOGIN ----------
            driver.get("https://release.v2.trans-ex.parallaxtec.dev/landing");
            test.info("Navigated to landing page");

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

            int min = 10000000; // smallest 8-digit number
            int max = 99999999; // largest 8-digit number
            int randomWaybill = (int)(Math.random() * (max - min + 1) + min);
            String waybillNumber = String.valueOf(randomWaybill);

            // Now input it
            WebElement waybill = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Enter the waybill number here']")
            ));
            waybill.sendKeys(waybillNumber);
            test.info("Generated 8-digit waybill number: " + waybillNumber);


            WebElement orderNo = driver.findElement(By.xpath("//input[@placeholder='Enter your order number']"));
            orderNo.sendKeys("1");

            WebElement receiver = driver.findElement(By.xpath("//input[@placeholder='Enter customer name']"));
            receiver.sendKeys("Test Customer");

            WebElement address = driver.findElement(By.xpath("//input[@placeholder='Enter address']"));
            address.sendKeys("Colombo");

            WebElement phone1 = driver.findElement(By.xpath("//input[@placeholder='Enter first phone number']"));
            phone1.sendKeys("0701825283");

            WebElement phone2 = driver.findElement(By.xpath("//input[@placeholder='Enter second phone number']"));
            phone2.sendKeys("0382244448");

            WebElement description = driver.findElement(By.xpath("//textarea[@placeholder='Enter order description']"));
            description.sendKeys("Test order 01");

            // Step 1: Click the dropdown input
            WebElement cityDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@type='text' and @readonly]")
            ));
            cityDropdown.click();

            // Step 2: Type the city name
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Search...']")
            ));
            searchInput.sendKeys("Colombo");

            // Optional short wait for results to appear
            Thread.sleep(1000);

            // Step 3: Select the correct city (label-based selector)
            WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//label[contains(text(),'Colombo 01')]")
            ));
            cityOption.click();





            WebElement cod = driver.findElement(By.xpath("//input[@placeholder='Enter COD amount']"));
            cod.sendKeys("6000");

            WebElement remarks = driver.findElement(By.xpath("//textarea[@placeholder='Enter remarks']"));
            remarks.sendKeys("Test remarks for order 01");

            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Place Order')]")
            ));
            submitBtn.click();


            test.pass("Order submitted successfully");

            // ✅ Wait for success confirmation popup (adjust XPath based on actual popup text)
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
