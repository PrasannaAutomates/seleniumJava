package com.automation.framework.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(css = "a[href='/logout']")
    private WebElement logoutButton;

    @FindBy(css = "h2")
    private WebElement header;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getHeaderText() {
        return header.getText();
    }

    public void logout() {
        logoutButton.click();
    }

    public boolean isAtHomePage() {
        return driver.getCurrentUrl().contains("/secure");
    }
}