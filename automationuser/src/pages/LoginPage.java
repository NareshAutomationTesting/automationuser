package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.ExcelReader;

public class LoginPage {
    private WebDriver driver;
    
    private By usernameField;
    private By passwordField;
    private By loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        //loadLocators();
    }

	/*
	 * private void loadLocators() { usernameField = getByLocator("usernameField");
	 * passwordField = getByLocator("passwordField"); loginButton =
	 * getByLocator("loginButton"); }
	 */

    private By getBy(String[] locator) {
        String type = locator[0];
        String value = locator[1];

        return switch (type.toLowerCase()) {
            case "id" -> By.id(value);
            case "name" -> By.name(value);
            case "xpath" -> By.xpath(value);
            case "css" -> By.cssSelector(value);
            case "classname" -> By.className(value);
            case "linktext" -> By.linkText(value);
            case "partiallinktext" -> By.partialLinkText(value);
            case "tagname" -> By.tagName(value);
            default -> throw new IllegalArgumentException("Invalid locator type: " + type);
        };
    }

    public void enterUsername(String username) {
        String[] locator = ExcelReader.getLocator("usernameField");
        WebElement usernameField = driver.findElement(getBy(locator));
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        String[] locator = ExcelReader.getLocator("passwordField");
        WebElement passwordField = driver.findElement(getBy(locator));
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        String[] locator = ExcelReader.getLocator("loginButton");
        WebElement loginButton = driver.findElement(getBy(locator));
        loginButton.click();
    }

    public void login(String username,String password) {
    	enterUsername(username);
    	enterPassword(password);
    	clickLoginButton();
    }
    
    public boolean isLoggedIn() {
        return driver.getCurrentUrl().contains("inventory.html");
    }
}
