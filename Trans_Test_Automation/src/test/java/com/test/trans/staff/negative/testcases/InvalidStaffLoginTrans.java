package com.test.trans.staff.negative.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class InvalidStaffLoginTrans {
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\HASINI\\Desktop\\Automation Trans Express Project\\TransExpressTest\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa2.transexpress.parallaxtec.dev");

    }
    // invalid email and password
    @Test(priority = 1)
    public void transStaffInvalidLogin() throws InterruptedException {

        // scroll down the page to select staff login button
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // define the number of steps and the pause duration between each scroll step
        int steps = 10;
        long pauseTime = 500;
        int scrollAmount = 100;

        for (int i = 0; i < steps; i++) {
            js.executeScript("window.scrollBy(0, " + scrollAmount + ");");
            Thread.sleep(pauseTime);
        }

        // login for staff portal
        WebElement staffLoginBtn = driver.findElement(By.xpath("//div[1]/div/div/div/a[1]"));
        staffLoginBtn.click();

        // locate the form element
        WebElement formElement = driver.findElement(By.tagName("h5"));

        // get the text of the form element
        String actualFormTitle = formElement.getText();

        // define the expected form title
        String expectedFormTitle = "Login to your account";

        // assert for the form title
        Assert.assertEquals(actualFormTitle, expectedFormTitle, "Form title does not match expected title");

        // for email field
        WebElement email = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        email.sendKeys("dsts123@gmail.com");
        Thread.sleep(200);

        // for password field
        WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        password.sendKeys("rrtr@336");
        Thread.sleep(200);

        //for login button
        WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"login_panel\"]/div[9]/button"));
        loginBtn.click();

        // Locate the error or alert message
        WebElement errorMessage = driver.findElement(By.xpath("//div[1]/div/div[2]/div/div[2]/form/div/div/div[2]"));

        // Verify the error message is displayed
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed");

        // Get the actual error message text and remove extra spaces
        String actualErrorMessage = errorMessage.getText();

        // Debugging: Print the actual error message text
        System.out.println("Actual Error Message for Invalid Email & Password: " + actualErrorMessage);

        // Verify the error message text
        String expectedErrorMessage = "Email or password is invalid.";
        // Verify the error message text
        boolean isErrorMessagePresent = actualErrorMessage.contains(expectedErrorMessage);
        Assert.assertTrue(isErrorMessagePresent, "Error message text does not match");

    }

    // valid email with invalid password
    @Test(priority = 2)
    public void transInvalidLoginPassword() throws InterruptedException {

        // for email field
        WebElement validEmail = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        validEmail.clear();
        validEmail.sendKeys("prasadika.l@parallax.lk");
        Thread.sleep(200);

        // for invalid password field
        WebElement invalidPassword = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        invalidPassword.sendKeys("ooc@6680");
        Thread.sleep(300);

        //for login button
        WebElement loginBtn = driver.findElement(By.xpath("//div[1]/div/div[2]/div/div[2]/form/div/div/div[10]/button"));
        loginBtn.click();

        // Locate the error or alert message
        WebElement errorMessage = driver.findElement(By.xpath("//div[1]/div/div[2]/div/div[2]/form/div/div/div[2]"));

        // Verify the error message is displayed
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed");

        // Get the actual error message text and remove extra spaces
        String actualErrorMessage = errorMessage.getText();

        // Debugging: Print the actual error message text
        System.out.println("Actual Error Message for Valid Email with Invalid Password: " + actualErrorMessage);

        // Verify the error message text
        String expectedErrorMessage = "Email or password is invalid.";
        // Verify the error message text
        boolean isErrorMessagePresent = actualErrorMessage.contains(expectedErrorMessage);
        Assert.assertTrue(isErrorMessagePresent, "Error message text does not match");

    }

    // invalid email with valid password
    @Test(priority = 3)
    public void invalidLoginEmail()throws InterruptedException{
        // for invalid email field
        WebElement invalidEmail = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        invalidEmail.clear();
        invalidEmail.sendKeys("abcd@gmail.com");
        Thread.sleep(200);

        // for password field
        WebElement validPassword = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        validPassword.clear();
        validPassword.sendKeys("12341234");
        Thread.sleep(300);

        //for login button
        WebElement loginBtn = driver.findElement(By.xpath("//div[1]/div/div[2]/div/div[2]/form/div/div/div[10]/button"));
        loginBtn.click();

        // Locate the error or alert message
        WebElement errorMessage = driver.findElement(By.xpath("//div[1]/div/div[2]/div/div[2]/form/div/div/div[2]"));

        // Verify the error message is displayed
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed");

        // Get the actual error message text and remove extra spaces
        String actualErrorMessage = errorMessage.getText();

        // print the actual error message text
        System.out.println("Actual Error Message for Invalid Email with Valid Password: " + actualErrorMessage);

        // Verify the error message text
        String expectedErrorMessage = "Email or password is invalid.";
        // Verify the error message text
        boolean isErrorMessagePresent = actualErrorMessage.contains(expectedErrorMessage);
        Assert.assertTrue(isErrorMessagePresent, "Error message text does not match");
    }

    // staff login password with less than 8 digits
    @Test(priority = 4)
    public void invalidPasswordPattern()throws InterruptedException{
        // for invalid email field
        WebElement email = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        email.clear();
        email.sendKeys("prasadika.l@parallax.lk");
        Thread.sleep(200);

        // for password field
        WebElement validPassword = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        validPassword.clear();
        validPassword.sendKeys("1234");
        Thread.sleep(300);

        //for login button
        WebElement loginBtn = driver.findElement(By.xpath("//div[1]/div/div[2]/div/div[2]/form/div/div/div[10]/button"));
        loginBtn.click();

        // Locate the error or alert message
        WebElement errorMessage = driver.findElement(By.xpath("//div[1]/div/div[2]/div/div[2]/form/div/div/div[2]"));

        // Verify the error message is displayed
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed");

        // Get the actual error message text and remove extra spaces
        String actualErrorMessage = errorMessage.getText();

        // print the actual error message text
        System.out.println("Actual Error Message Less than 8 Characters for Password Field: " + actualErrorMessage);

        // Verify the error message text
        String expectedErrorMessage = "The password must be at least 8 characters.";
        // Verify the error message text
        boolean isErrorMessagePresent = actualErrorMessage.contains(expectedErrorMessage);
        Assert.assertTrue(isErrorMessagePresent, "Error message text does not match");
    }

    @AfterClass
    public void browserQuit() {
        if (driver != null) {
            driver.quit();
        }
    }


}
