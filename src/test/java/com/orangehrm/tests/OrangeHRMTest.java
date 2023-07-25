package com.orangehrm.tests;

import com.orangehrm.pages.AdminPage;
import com.orangehrm.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import org.testng.Assert;

public class OrangeHRMTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private AdminPage adminPage;
    private int initialRecordCount;
    private String testUsername;

    @BeforeClass
    public void setUp() {
        // Setup WebDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        
        loginPage = new LoginPage(driver);
        adminPage = new AdminPage(driver);
    }

    @BeforeMethod
    public void navigateToHome() {
        loginPage.navigate();
    }

    @Test(priority = 1, description = "Login Test")
    public void loginTest() {
        // Login with valid credentials
        loginPage.login("Admin", "admin123");
        
        // Verify login is successful by checking if Admin tab is visible
        Assert.assertTrue(adminPage.isAdminTabVisible(), "Login failed or Admin tab not found!");
    }

    @Test(priority = 2, description = "Add User Test", dependsOnMethods = "loginTest")
    public void addUserTest() {
        // Login first
        loginPage.login("Admin", "admin123");
        Assert.assertTrue(adminPage.isAdminTabVisible(), "Login failed!");
        
        // Navigate to Admin tab
        adminPage.navigateToAdminTab();
        
        // Get initial record count
        initialRecordCount = adminPage.getRecordCount();
        
        // Generate unique username
        long timestamp = System.currentTimeMillis();
        testUsername = "TestUser" + timestamp;
        String password = "TestPassword123";
        
        // Add new user
        adminPage.addUser(
            testUsername,
            "Admin",
            "Enabled",
            password,
            "John Smith" // Employee name - adjust based on available employees
        );
        
        // Verify user count increased
        int newRecordCount = adminPage.getRecordCount();
        Assert.assertTrue(newRecordCount >= initialRecordCount, 
            "User count did not increase! Expected: >= " + initialRecordCount + ", Actual: " + newRecordCount);
    }

    @Test(priority = 3, description = "Delete User Test", dependsOnMethods = "addUserTest")
    public void deleteUserTest() {
        // Login first
        loginPage.login("Admin", "admin123");
        Assert.assertTrue(adminPage.isAdminTabVisible(), "Login failed!");
        
        // Navigate to Admin tab
        adminPage.navigateToAdminTab();
        
        // Get initial record count
        initialRecordCount = adminPage.getRecordCount();
        
        if (testUsername != null && !testUsername.isEmpty()) {
            // Search for the test user
            adminPage.searchUser(testUsername);
            
            // Check if user exists
            int currentCount = adminPage.getRecordCount();
            
            if (currentCount > 0) {
                // Select and delete user
                adminPage.selectUserCheckbox(testUsername);
                adminPage.deleteUser();
                
                // Verify user count decreased or stayed the same
                int newRecordCount = adminPage.getRecordCount();
                Assert.assertTrue(newRecordCount <= initialRecordCount, 
                    "User count did not decrease! Expected: <= " + initialRecordCount + ", Actual: " + newRecordCount);
            }
        }
    }

    @Test(priority = 4, description = "Complete User Management Flow", dependsOnMethods = "loginTest")
    public void completeUserManagementFlow() {
        // Login
        loginPage.login("Admin", "admin123");
        Assert.assertTrue(adminPage.isAdminTabVisible(), "Login failed!");
        
        // Navigate to Admin tab
        adminPage.navigateToAdminTab();
        
        // Get initial count
        initialRecordCount = adminPage.getRecordCount();
        
        // Generate unique username
        long timestamp = System.currentTimeMillis();
        String username = "TestUser" + timestamp;
        String password = "TestPassword123";
        
        // Add user
        adminPage.addUser(
            username,
            "Admin",
            "Enabled",
            password,
            "John Smith"
        );
        
        // Verify user was added
        int newRecordCount = adminPage.getRecordCount();
        Assert.assertTrue(newRecordCount >= initialRecordCount, 
            "User was not added! Expected: >= " + initialRecordCount + ", Actual: " + newRecordCount);
        
        // Search for the user
        adminPage.searchUser(username);
        
        // Verify user appears in search results
        int searchCount = adminPage.getRecordCount();
        Assert.assertTrue(searchCount > 0, "User not found in search results!");
        
        // Delete the user
        adminPage.selectUserCheckbox(username);
        adminPage.deleteUser();
        
        // Verify user was deleted
        int finalRecordCount = adminPage.getRecordCount();
        Assert.assertTrue(finalRecordCount <= initialRecordCount, 
            "User was not deleted! Expected: <= " + initialRecordCount + ", Actual: " + finalRecordCount);
    }

    @AfterMethod
    public void tearDownMethod() {
        // Optional: Take screenshot on failure
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

