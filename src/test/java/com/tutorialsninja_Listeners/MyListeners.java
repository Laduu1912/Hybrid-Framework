package com.tutorialsninja_Listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.observer.ExtentObserver;

public class MyListeners<ExtentHtmlReporter> implements ITestListener{
public ExtentReports extentReport;	
public String testName;
public ExtentTest extentTest;
public WebDriver driver;

	

@Override
public void onStart(ITestContext context) {
    System.out.println("Project execution started");

    extentReport = new ExtentReports();
    
    ExtentObserver<?> htmlReporter = null;
	extentReport.attachReporter(htmlReporter);
}

@Override
public void onTestStart(ITestResult result) {
    testName = result.getName();
    System.out.println(testName + " -----> Test Execution Started");
    extentTest = extentReport.createTest(testName);
    extentTest.log(Status.INFO, testName + " -----> Test Execution Started");
}

@Override
public void onTestSuccess(ITestResult result) {
    testName = result.getName();
    System.out.println(testName + " ---> Executed successfully");
    extentTest.log(Status.PASS, testName + " ---> Executed successfully");
}

@Override
public void onTestFailure(ITestResult result) {
    testName = result.getName();
    driver = (WebDriver) result.getTestContext().getAttribute("driver"); // Assuming you set the driver as an attribute in your test context

    File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    String destinationFile = "test-output/Screenshots/" + testName + ".png";

    try {
        FileHandler.copy(source, new File(destinationFile));
    } catch (IOException e) {
        e.printStackTrace();
    }

    extentTest.addScreenCaptureFromPath(destinationFile);
    System.out.println("Screenshot taken");
    System.out.println(result.getThrowable());
    System.out.println(testName + " ---> Failed");
}

@Override
public void onTestSkipped(ITestResult result) {
    testName = result.getName();
    System.out.println(testName + " ---> Skipped");
    System.out.println(result.getThrowable());
}

@Override
public void onFinish(ITestContext context) {
    System.out.println("Project Execution Ends");
    extentReport.flush();

    File extentReportFile = new File("test-output/ExtentReports/extentreport.html");
    try {
        Desktop.getDesktop().browse(extentReportFile.toURI());
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}