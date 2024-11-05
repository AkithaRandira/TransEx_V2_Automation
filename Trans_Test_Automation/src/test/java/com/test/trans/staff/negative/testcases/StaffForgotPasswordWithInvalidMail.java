package com.test.trans.staff.negative.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class StaffForgotPasswordWithInvalidMail {
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\HASINI\\Desktop\\Automation Trans Express Project\\TransExpressTest\\src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa2.transexpress.parallaxtec.dev");

    }

    @Test(priority = 1)
    public void staffForgotPasswordWithInvalidEmail()throws InterruptedException{

        // scroll down the page to select client login button
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // define the number of steps and the pause duration between each scroll step
        int steps = 03;
        long pauseTime = 100;
        int scrollAmount = 50;

        for(int i = 0; i < steps; i ++){
            js.executeScript("window.scrollBy(0, "+scrollAmount+");");
            Thread.sleep(pauseTime);
        }

        // login for client portal
        WebElement staffLoginBtn = driver.findElement(By.xpath("//div[1]/div/div/div/a[2]"));
        staffLoginBtn.click();

        WebElement forgotPwd = driver.findElement(By.xpath("//div[1]/div/div[2]/div/div[2]/form/div/div/div[8]/div/div[2]/a"));
        forgotPwd.click();

        // locate the password rest form title
        WebElement formTitle = driver.findElement(By.xpath("//div[2]/div/div/div/div[1]"));

        String actualTitle = formTitle.getText();

        // define the expected form title
        String expectedTitle = ("Staff Reset Password");

        // assert for the form title
        Assert.assertEquals(actualTitle, expectedTitle, "Form title does not match expected title");

        // to print actual form title
        System.out.println("Form title is displayed as expected: " + expectedTitle);

        // to enter staff invalid email
        WebElement emailField = driver.findElement(By.xpath("//div[2]/div/div/div/div[2]/form/div[1]/div/input"));
        emailField.sendKeys("abdsf@gmail.com");
        Thread.sleep(200);

        // for confirm button
        WebElement confirmBtn = driver.findElement(By.xpath("//div[2]/div/div/div/div[2]/form/div[2]/div/button"));
        confirmBtn.click();

        // locate the success message
        WebElement errorMessage = driver.findElement(By.xpath("//div[2]/div/div/div/div[2]/form/div[1]/div/span/strong"));

        // Verify the success message is displayed
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed");

        // Get the actual success message text
        String actualErrorMessage = errorMessage.getText();

        // print the actual success message text
        System.out.println("Actual Error Message: " + actualErrorMessage);

        // Verify the error message text
        String expectedErrorMessage = "We can't find a user with that e-mail address.";

        // Verify the error message text
        boolean isErrorMessagePresent = actualErrorMessage.contains(expectedErrorMessage);
        Assert.assertTrue(isErrorMessagePresent, "Error message text does not match");

    }
}
