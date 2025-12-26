package com.automation.framework.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.framework.utils.ConfigReader;
import com.automation.framework.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBTest {
    private static final Logger logger = LoggerFactory.getLogger(DBTest.class);

    @Test
    public void testDBConnectionAndQuery() throws SQLException {
        logger.info("Starting DB connection test");
        String url = ConfigReader.getProperty("db.url");
        String username = ConfigReader.getProperty("db.username");
        String password = ConfigReader.getProperty("db.password");

        Connection conn = DBUtils.getConnection(url, username, password);
        Assert.assertNotNull(conn, "Database connection should be established");

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as userCount FROM users");

        int userCount = 0;
        if (rs.next()) {
            userCount = rs.getInt("userCount");
        }
        Assert.assertTrue(userCount >= 0, "User count should be non-negative");
        logger.info("Total users in database: " + userCount);

        rs.close();
        stmt.close();
        DBUtils.closeConnection();
        logger.info("DB test completed successfully");
    }

    @Test
    public void testDBInsertAndVerify() throws SQLException {
        logger.info("Starting DB insert test");
        String url = ConfigReader.getProperty("db.url");
        String username = ConfigReader.getProperty("db.username");
        String password = ConfigReader.getProperty("db.password");

        Connection conn = DBUtils.getConnection(url, username, password);

        // Insert a test user
        String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertQuery);
        String testUser = "testuser" + System.currentTimeMillis();
        String testPass = "testpass";
        pstmt.setString(1, testUser);
        pstmt.setString(2, testPass);
        int rowsAffected = pstmt.executeUpdate();
        Assert.assertEquals(rowsAffected, 1, "One row should be inserted");

        // Verify insertion
        String selectQuery = "SELECT * FROM users WHERE username = ?";
        pstmt = conn.prepareStatement(selectQuery);
        pstmt.setString(1, testUser);
        ResultSet rs = pstmt.executeQuery();
        Assert.assertTrue(rs.next(), "User should be found in database");
        Assert.assertEquals(rs.getString("username"), testUser, "Username should match");

        rs.close();
        pstmt.close();
        DBUtils.closeConnection();
        logger.info("DB insert and verify test completed successfully");
    }
}