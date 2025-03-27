package com.test.trans.client.positive.testcases;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Random;

public class AddNewBulkOrder {

    WebDriver driver;
    WebDriverWait wait;
    ExtentReports extent;
    ExtentTest test;
    Actions actions;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "D:\\Parallax\\TransEx_V2_Automation\\Trans_Test_Automation\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        actions = new Actions(driver);

        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/AddNewBulkOrderReport.html");
        spark.config().setDocumentTitle("Add Bulk Order Automation Report");
        spark.config().setReportName("Bulk Order");
        spark.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(spark);
        test = extent.createTest("Add New Bulk Order");
    }

    @Test
    public void addBulkOrder() {
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

            // ---------- BULK UPLOAD ----------
            WebElement addOrder = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='side-menu__title' and text()='Add New Order']")
            ));
            addOrder.click();
            test.pass("Clicked 'Add New Order'");

            WebElement bulkOrderTab = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Bulk Order')]")
            ));
            bulkOrderTab.click();
            test.pass("Switched to Bulk Order tab");

            WebElement bulkUploadBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Bulk Upload')]")
            ));
            bulkUploadBtn.click();
            test.pass("Clicked Bulk Upload");

            // Wait for Excel-like sheet to load
            Thread.sleep(5000); // You can replace this with an explicit wait on the sheet container if possible

            // ---------- INPUT VALUES IN SHEET ----------
            // 🔁 Modify these locators as per your implementation
            WebElement cellWaybill = driver.findElement(By.xpath("//div[@data-row='0'][@data-col='0']"));
            actions.doubleClick(cellWaybill).perform();
            cellWaybill.sendKeys(generateRandomWaybill());
            actions.sendKeys(Keys.TAB).perform();

            WebElement cellOrderNo = driver.switchTo().activeElement();
            cellOrderNo.sendKeys("1");
            actions.sendKeys(Keys.TAB).perform();

            WebElement cellName = driver.switchTo().activeElement();
            cellName.sendKeys("Bulk User");
            actions.sendKeys(Keys.TAB).perform();

            WebElement cellAddress = driver.switchTo().activeElement();
            cellAddress.sendKeys("Colombo");
            actions.sendKeys(Keys.TAB).perform();

            WebElement cellDescription = driver.switchTo().activeElement();
            cellDescription.sendKeys("Bulk Order Desc");
            actions.sendKeys(Keys.TAB).perform();

            WebElement phone1 = driver.switchTo().activeElement();
            phone1.sendKeys("0771234567");
            actions.sendKeys(Keys.TAB).perform();

            WebElement phone2 = driver.switchTo().activeElement();
            phone2.sendKeys("0719876543");
            actions.sendKeys(Keys.TAB).perform();

            ((JavascriptExecutor) driver).executeScript("window.scrollBy(1000,0)");

            WebElement cod = driver.switchTo().activeElement();
            cod.sendKeys("5000");
            actions.sendKeys(Keys.TAB).perform();

            WebElement city = driver.switchTo().activeElement();
            city.sendKeys("Colombo");
            actions.sendKeys(Keys.TAB).perform();

            WebElement remarks = driver.switchTo().activeElement();
            remarks.sendKeys("Auto bulk upload");

            // ---------- SUBMIT ----------
            WebElement uploadBtn = driver.findElement(By.id("upload"));
            uploadBtn.click();
            test.pass("Clicked Upload button");

            // ✅ Confirm success popup
            WebElement successPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Order(s) submitted successfully')]")
            ));
            Assert.assertTrue(successPopup.isDisplayed(), "Success popup not visible!");
            test.pass("Success popup displayed!");

        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
        }
    }

    private String generateRandomWaybill() {
        Random rand = new Random();
        int num = 10000000 + rand.nextInt(90000000);
        return String.valueOf(num);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }
}
