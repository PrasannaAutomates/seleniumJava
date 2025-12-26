package com.automation.framework.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.framework.pages.HomePage;
import com.automation.framework.pages.LoginPage;
import com.automation.framework.utils.ExcelUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LoginTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException {
        List<Map<String, String>> data = ExcelUtils.getTestData("src/test/resources/testdata.xlsx", "Login");
        Object[][] testData = new Object[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            testData[i][0] = data.get(i).get("username");
            testData[i][1] = data.get(i).get("password");
        }
        return testData;
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password) {
        logger.info("Starting login test with username: " + username);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        if ("tomsmith".equals(username) && "SuperSecretPassword!".equals(password)) {
            Assert.assertTrue(loginPage.isLoginSuccessful(), "Login should be successful");
            HomePage homePage = new HomePage(driver);
            Assert.assertEquals(homePage.getHeaderText(), "Secure Area", "Header should be 'Secure Area'");
            logger.info("Login successful for user: " + username);
        } else {
            Assert.assertFalse(loginPage.isLoginSuccessful(), "Login should fail for invalid credentials");
            String message = loginPage.getFlashMessage();
            Assert.assertTrue(message.contains("Your username is invalid!") || message.contains("Your password is invalid!"), "Error message should be displayed");
            logger.info("Login failed as expected for user: " + username);
        }
    }
}