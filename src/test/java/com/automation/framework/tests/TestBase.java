package com.automation.framework.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.automation.framework.pages.BasePage;
import com.automation.framework.utils.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestBase {
    protected WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeSuite
    public void beforeSuite() {
        BasePage.createScreenshotDir();
        logger.info("Test suite started");
    }

    @BeforeMethod
    public void setUp() {
        String browser = ConfigReader.getProperty("browser");
        logger.info("Setting up browser: " + browser);
        if ("chrome".equalsIgnoreCase(browser)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        String url = ConfigReader.getProperty("url");
        driver.get(url);
        logger.info("Navigated to: " + url);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                BasePage.takeScreenshot(driver, result.getName());
            } catch (IOException e) {
                logger.error("Failed to take screenshot", e);
            }
        }
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed");
        }
    }
}