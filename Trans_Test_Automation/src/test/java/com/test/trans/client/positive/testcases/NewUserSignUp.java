package com.test.trans.client.positive.testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

import java.time.Duration;

public class NewUserSignUp {

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

        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/NewUserSignUpReport.html");
        spark.config().setDocumentTitle("New User SignUp Report");
        spark.config().setReportName("Sign Up Flow");
        spark.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(spark);

        test = extent.createTest("New User Signup");
    }

    @Test
    public void signUpFlow() {
        try {
            // ---------- OPEN PAGE ----------
            driver.get("https://release.v2.trans-ex.parallaxtec.dev/landing");
            test.info("Navigated to landing page");

            // Wait for loading to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'LOADING') or contains(@class,'loading')]")
            ));

            // Wait and click "Client Login"
            WebElement clientLoginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//button[contains(text(),'Client Login')]")
            ));
            wait.until(ExpectedConditions.elementToBeClickable(clientLoginBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clientLoginBtn);
            test.pass("Clicked Client Login");

            // Click on "Register Now"
            WebElement registerBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Register Now')]")
            ));
            registerBtn.click();
            test.pass("Clicked 'Register Now'");


            // Step 1 - Fill Owner Details
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Create Account')]")
            ));



// Generate unique email (timestamp ensures uniqueness)
            String ownerEmail = "owner" + System.currentTimeMillis() + "@test.com";

            driver.findElement(By.xpath("//input[@placeholder='Enter owner name']")).sendKeys("AkiRanaDev");
            driver.findElement(By.xpath("//input[@placeholder='Enter owner NIC number']")).sendKeys("991234567V");
            driver.findElement(By.xpath("//input[@placeholder='Enter owner email address']")).sendKeys(ownerEmail);
            driver.findElement(By.xpath("//input[@placeholder='Enter owner contact no']")).sendKeys("0771234567");
            driver.findElement(By.xpath("//input[@placeholder='Enter advicer name']")).sendKeys("Referred by QA");
            driver.findElement(By.xpath("//textarea[@placeholder='Enter owner address']")).sendKeys("Colombo, Sri Lanka");

            test.pass("Filled Step 1 - Owner Details with email: " + ownerEmail);

// Click Next
            WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Next')]")
            ));
            nextBtn.click();
            test.pass("Clicked 'Next' to proceed to Step 2");

            // Wait for Step 2 - Business Details page to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Business Details')]")
            ));

// Fill out the business form
            driver.findElement(By.xpath("//input[@placeholder='Enter business name']")).sendKeys("Test Business");
            driver.findElement(By.xpath("//input[@placeholder='Enter business registration ID']")).sendKeys("BR123456");
            driver.findElement(By.xpath("//input[@placeholder='Enter your email address']")).sendKeys("biz" + System.currentTimeMillis() + "@test.com");
            driver.findElement(By.xpath("//input[@placeholder='Enter your business phone no']")).sendKeys("0772345678");
            driver.findElement(By.xpath("//input[@placeholder='Enter the password']")).sendKeys("Test@1234");
            driver.findElement(By.xpath("//input[@placeholder='Confirm password']")).sendKeys("Test@1234");

// Step: Click the Business Type dropdown
            WebElement businessTypeDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@placeholder='Click to select...']")
            ));
            businessTypeDropdown.click();
            test.pass("Clicked Business Type dropdown");

// Step: Select 'Electronics' from the list
            WebElement electronicsOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//label[contains(text(),'Electronics')]")
            ));
            electronicsOption.click();
            test.pass("Selected 'Electronics' as Business Type");



// Address field
            driver.findElement(By.xpath("//input[@placeholder='Enter address']")).sendKeys("Colombo, Sri Lanka");


// Click Next
            WebElement nextBtnStep2 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Next')]")
            ));
            nextBtnStep2.click();
            test.pass("Filled Business Details and proceeded to Step 3");


            // Wait for Step 3 header to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'text-2xl') and contains(text(),'Create Account')]")
            ));

// Fill pickup phone number
            driver.findElement(By.xpath("//input[@placeholder='Enter your pickup phone no']")).sendKeys("0777654321");

// Select Nearest City
            WebElement cityDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@placeholder='Select nearest city' and @readonly]")
            ));
            cityDropdown.click();

            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Search...']")
            ));
            searchInput.sendKeys("Colombo");

// Wait for option and click
            WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//label[contains(text(),'Colombo 01')]")
            ));
            cityOption.click();

// Fill Pickup Branch
            driver.findElement(By.xpath("//input[@placeholder='Enter pickup branch']")).sendKeys("Main Hub");

// Fill Pickup Address
            driver.findElement(By.xpath("//input[@placeholder='Enter pickup address']")).sendKeys("Warehouse Street, Colombo");

// Click Next to go to Step 4
            WebElement nextBtn3 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Next')]")
            ));
            nextBtn3.click();

            test.pass("Filled Step 3 - Pickup Details and proceeded to Step 4 ✅");


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
