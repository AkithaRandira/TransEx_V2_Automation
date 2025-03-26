package com.test.trans.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.annotations.Test;

public class ReportsDemo {

    @Test
    public void generateReport() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Login Test Results");
        sparkReporter.config().setTheme(Theme.STANDARD);

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        ExtentTest test = extent.createTest("Client Login Test");
        test.pass("Login test executed successfully");

        extent.flush();
    }
}
