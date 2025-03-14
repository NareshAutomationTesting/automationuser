package test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import base.BaseTest;
import pages.LoginPage;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NewTest extends BaseTest {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static final String SCREENSHOT_DIR = "test-output/ExtentReports/screenshots/";

    @BeforeClass
    public void setupReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReports/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        test = extent.createTest("Login Test", "Validates user login functionality");

        // Ensure screenshot directory exists
        try {
            Files.createDirectories(Paths.get(SCREENSHOT_DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testValidLogin() {
        test.info("Starting Login Test");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        boolean isLoggedIn = loginPage.isLoggedIn();
        
        String screenshotPath = takeScreenshot(isLoggedIn ? "login_success" : "login_failure");
        
        if (isLoggedIn) {
            test.pass("Login successful", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            test.fail("Login failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }
        
        Assert.assertTrue(isLoggedIn, "Login failed");
    }

    public String takeScreenshot(String screenshotName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = SCREENSHOT_DIR + screenshotName + ".png";
        File destFile = new File(screenshotPath);
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destFile.getAbsolutePath();
    }

    @AfterClass
    public void tearDownReport() {
        extent.flush();
    }
}
