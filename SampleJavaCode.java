import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SampleJavaCode {

	@Test
	public void jenkinsTest() {

		
		        WebDriverManager.chromedriver().setup();
		        WebDriver driver = new ChromeDriver();
		        driver.manage().window().maximize();
		        driver.get("https://www.saucedemo.com/");
		        driver.quit();
			System.out.println("Java Selenium Program executed successfully!");
		    
	}
}
