package test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.ExcelReader;
import utils.Utils;

public class LoginTest extends BaseTest {
	
	 @Test(dataProvider = "loginData")
	    public void testLogin(String username, String password) {
	        LoginPage loginPage = new LoginPage(driver);
	        loginPage.login(username, password);
	        boolean isLoggedIn = loginPage.isLoggedIn();
	        Assert.assertTrue(false, "Login failed for user: " + username);
	        updateReport(isLoggedIn);
	    }

    
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return ExcelReader.readExcelData("test-data/LoginData.xlsx", "Sheet1");
    }
}
