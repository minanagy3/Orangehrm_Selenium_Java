package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdminPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "menu_admin_viewAdminModule")
    private WebElement adminTab;

    @FindBy(id = "btnAdd")
    private WebElement addUserButton;

    @FindBy(id = "recordsFound")
    private WebElement userCountText;

    @FindBy(id = "systemUser_userName")
    private WebElement usernameField;

    @FindBy(id = "btnSave")
    private WebElement saveButton;

    @FindBy(id = "searchBtn")
    private WebElement searchButton;

    @FindBy(id = "btnDelete")
    private WebElement deleteButton;

    @FindBy(id = "systemUser_employeeName_empName")
    private WebElement employeeNameField;

    @FindBy(id = "systemUser_password")
    private WebElement passwordField;

    @FindBy(id = "systemUser_confirmPassword")
    private WebElement confirmPasswordField;

    @FindBy(id = "systemUser_userType")
    private WebElement userRoleDropdown;

    @FindBy(id = "systemUser_status")
    private WebElement statusDropdown;

    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateToAdminTab() {
        wait.until(ExpectedConditions.elementToBeClickable(adminTab));
        adminTab.click();
        wait.until(ExpectedConditions.urlContains("admin"));
    }

    public boolean isAdminTabVisible() {
        try {
            return adminTab.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getRecordCount() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("recordsFound")));
            String text = userCountText.getText();
            // Extract number from text like "No Records Found" or "1 Record Found"
            if (text.contains("No Records")) {
                return 0;
            }
            String[] parts = text.split(" ");
            for (String part : parts) {
                try {
                    return Integer.parseInt(part);
                } catch (NumberFormatException e) {
                    // Continue to next part
                }
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public void addUser(String username, String role, String status, String password, String employeeName) {
        wait.until(ExpectedConditions.elementToBeClickable(addUserButton));
        addUserButton.click();
        wait.until(ExpectedConditions.visibilityOf(usernameField));

        if (employeeName != null && !employeeName.isEmpty()) {
            employeeNameField.clear();
            employeeNameField.sendKeys(employeeName);
            // Wait for autocomplete and select first option
            try {
                Thread.sleep(1000);
                employeeNameField.sendKeys(org.openqa.selenium.Keys.ARROW_DOWN);
                employeeNameField.sendKeys(org.openqa.selenium.Keys.ENTER);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        usernameField.clear();
        usernameField.sendKeys(username);

        passwordField.clear();
        passwordField.sendKeys(password);

        confirmPasswordField.clear();
        confirmPasswordField.sendKeys(password);

        if (role != null && !role.isEmpty()) {
            Select roleSelect = new Select(userRoleDropdown);
            roleSelect.selectByVisibleText(role);
        }

        if (status != null && !status.isEmpty()) {
            Select statusSelect = new Select(statusDropdown);
            statusSelect.selectByVisibleText(status);
        }

        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();
        wait.until(ExpectedConditions.urlContains("admin"));
    }

    public void searchUser(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.clear();
        usernameField.sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
        wait.until(ExpectedConditions.urlContains("admin"));
    }

    public void deleteUser() {
        // First, select the checkbox for the user
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox'][name='chkSelectRow[]']"));
        if (!checkboxes.isEmpty()) {
            checkboxes.get(0).click();
        }

        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        deleteButton.click();

        // Confirm deletion in dialog
        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("dialogDeleteBtn")));
        confirmButton.click();
        wait.until(ExpectedConditions.urlContains("admin"));
    }

    public void selectUserCheckbox(String username) {
        // Find the row with the username and select its checkbox
        WebElement row = driver.findElement(By.xpath("//td[contains(text(), '" + username + "')]/.."));
        WebElement checkbox = row.findElement(By.cssSelector("input[type='checkbox']"));
        checkbox.click();
    }
}

