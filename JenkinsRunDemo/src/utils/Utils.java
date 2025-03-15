package utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utils {
	
    private static final String SCREENSHOT_DIR = "test-output/ExtentReports/screenshots/";
	
	 public static String takeScreenshot(String screenshotName,WebDriver driver) {
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


}
