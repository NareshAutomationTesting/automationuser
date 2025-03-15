package base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.Utils;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseTest {
    protected WebDriver driver;
    private static ExtentReports extent;
    private static ExtentTest test;
    private static final String SCREENSHOT_DIR = "test-output/ExtentReports/screenshots/";
    
    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }
    
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


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    public void updateReport(boolean result) {
    	
    	
    	 String screenshotPath = Utils.takeScreenshot(result ? "login_success" : "login_failure",driver);
         
         if (result) {
             test.pass("Login successful", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
         } else {
             test.fail("Login failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
         }
    	 }
   
    @AfterClass
    public void tearDownReport() {
        extent.flush();
    }

}
