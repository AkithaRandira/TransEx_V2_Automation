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
            driver.get("https://release.v2.trans-ex.parallaxtec.dev/landing");
            test.info("Navigated to landing page");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'LOADING') or contains(@class,'loading')]")
            ));

            WebElement clientLoginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//button[contains(text(),'Client Login')]")
            ));
            wait.until(ExpectedConditions.elementToBeClickable(clientLoginBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clientLoginBtn);
            test.pass("Clicked Client Login");

            WebElement registerBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Register Now')]")
            ));
            registerBtn.click();
            test.pass("Clicked 'Register Now'");

            // ---------- Step 1 - Owner Details ----------
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Create Account')]")
            ));

            String ownerEmail = "owner" + System.currentTimeMillis() + "@test.com";

            driver.findElement(By.xpath("//input[@placeholder='Enter owner name']")).sendKeys("AkiRanaDev");
            driver.findElement(By.xpath("//input[@placeholder='Enter owner NIC number']")).sendKeys("991234567V");
            driver.findElement(By.xpath("//input[@placeholder='Enter owner email address']")).sendKeys(ownerEmail);
            driver.findElement(By.xpath("//input[@placeholder='Enter owner contact no']")).sendKeys("0771234567");
            driver.findElement(By.xpath("//input[@placeholder='Enter advicer name']")).sendKeys("Referred by QA");
            driver.findElement(By.xpath("//textarea[@placeholder='Enter owner address']")).sendKeys("Colombo, Sri Lanka");
            test.pass("Filled Step 1 - Owner Details with email: " + ownerEmail);

            WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Next')]")
            ));
            nextBtn.click();
            test.pass("Clicked 'Next' to proceed to Step 2");

            // ---------- Step 2 - Business Details ----------
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Business Details')]")
            ));

            driver.findElement(By.xpath("//input[@placeholder='Enter business name']")).sendKeys("Test Business");
            driver.findElement(By.xpath("//input[@placeholder='Enter business registration ID']")).sendKeys("BR123456");
            driver.findElement(By.xpath("//input[@placeholder='Enter your email address']")).sendKeys("biz" + System.currentTimeMillis() + "@test.com");
            driver.findElement(By.xpath("//input[@placeholder='Enter your business phone no']")).sendKeys("0772345678");
            driver.findElement(By.xpath("//input[@placeholder='Enter the password']")).sendKeys("Test@1234");
            driver.findElement(By.xpath("//input[@placeholder='Confirm password']")).sendKeys("Test@1234");

            WebElement businessTypeDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@placeholder='Click to select...']")
            ));
            businessTypeDropdown.click();
            test.pass("Clicked Business Type dropdown");

            WebElement electronicsOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//label[contains(text(),'Electronics')]")
            ));
            electronicsOption.click();
            test.pass("Selected 'Electronics' as Business Type");

            driver.findElement(By.xpath("//input[@placeholder='Enter address']")).sendKeys("Colombo, Sri Lanka");

            WebElement nextBtnStep2 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Next')]")
            ));
            nextBtnStep2.click();
            test.pass("Filled Business Details and proceeded to Step 3");

            // ---------- Step 3 - Pickup Details ----------
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'text-2xl') and contains(text(),'Create Account')]")
            ));

            driver.findElement(By.xpath("//input[@placeholder='Enter your pickup phone no']")).sendKeys("0771234567");

            WebElement nearestCityInput = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@placeholder='Select nearest city' and @readonly]")
            ));
            nearestCityInput.click();
            test.info("Clicked nearest city dropdown");

            Thread.sleep(1000); // Allow options to load

            WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//label[contains(text(),'Colombo 01')]")
            ));
            cityOption.click();
            test.pass("Selected 'Colombo 01' from dropdown");

            // Wait until the Pickup Branch input is populated with auto value
            WebElement pickupBranchField = wait.until(driver -> {
                try {
                    WebElement el = driver.findElement(By.xpath("//input[@placeholder='Pickup Branch based on Nearest City']"));
                    String val = el.getAttribute("value");
                    return (val != null && !val.trim().isEmpty()) ? el : null;
                } catch (Exception e) {
                    return null;
                }
            });
            test.pass("Pickup Branch auto-filled as: " + pickupBranchField.getAttribute("value"));


            // ✅ Enter pickup address (this is a <textarea>)
            WebElement pickupAddress = driver.findElement(By.xpath("//textarea[@placeholder='Enter pickup address']"));
            pickupAddress.sendKeys("Panadura Office");

            WebElement nextBtnStep3 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Next')]")
            ));
            nextBtnStep3.click();
            test.pass("Step 3 completed and moved to Step 4");


            // Step 4 - Wait for Create Account Step 4 to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'text-2xl') and contains(text(),'Create Account')]")
            ));

// Fill Delivery Email and Financial Email
            String deliveryEmail = "delivery" + System.currentTimeMillis() + "@test.com";
            String financialEmail = "finance" + System.currentTimeMillis() + "@test.com";

            driver.findElement(By.xpath("//input[@placeholder='Enter delivery email address']")).sendKeys(deliveryEmail);
            driver.findElement(By.xpath("//input[@placeholder='Enter financial email address']")).sendKeys(financialEmail);
            test.pass("Filled Delivery Email: " + deliveryEmail + " and Financial Email: " + financialEmail);

// Click Next
            WebElement nextBtnStep4 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Next')]")
            ));
            nextBtnStep4.click();
            test.pass("Step 4 completed and moved to Step 5");

            // Step 5 - Wait until "Bank Account Details" section appears
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'text-2xl') and contains(text(),'Create Account')]")
            ));

// Fill in the form
            driver.findElement(By.xpath("//input[@placeholder='Enter your bank account name']")).sendKeys("Aki Rana");
            driver.findElement(By.xpath("//input[@placeholder='Enter your account no']")).sendKeys("123456789012");
            driver.findElement(By.xpath("//input[@placeholder='Enter branch name']")).sendKeys("Colombo Main");
            driver.findElement(By.xpath("//input[@placeholder='Enter branch id']")).sendKeys("CM123");
            driver.findElement(By.xpath("//input[@placeholder='Enter bank name']")).sendKeys("Bank of QA");

            test.pass("Filled all bank account details");

// Click "Create"
            WebElement createBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Create')]")
            ));
            createBtn.click();
            test.pass("Clicked 'Create' to submit the form");

// Optional: Confirm success
            // Confirm successful signup message (adjust the text based on actual confirmation)
            WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'successfully') or contains(text(),'Success') or contains(text(),'created')]")
            ));

            Assert.assertTrue(successMsg.isDisplayed(), "Success message not displayed!");
            test.pass("Signup completed and success message displayed: " + successMsg.getText());

            test.pass("Signup completed successfully 🎉");



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
