# OrangeHRM Automation Tests - Selenium Java

This project contains automated tests for OrangeHRM using Selenium WebDriver, TestNG, Maven, and Page Object Model (POM) design pattern.

## 📋 Requirements

- Java 11 or higher
- Maven 3.6 or higher
- Chrome browser (latest version)

## 🚀 Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/Orangehrm_Selenium_Java.git
   cd Orangehrm_Selenium_Java
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install
   ```

## 📁 Project Structure

```
Orangehrm_Selenium_Java/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── orangehrm/
│       │           ├── pages/          # Page Object Model classes
│       │           │   ├── LoginPage.java
│       │           │   └── AdminPage.java
│       │           └── tests/          # Test classes
│       │               └── OrangeHRMTest.java
│       └── resources/
│           └── testng.xml
├── pom.xml
├── testng.xml
└── README.md
```

## 🧪 Test Cases

The project includes the following test cases:

1. **Login Test** - Tests user login functionality with valid credentials
2. **Add User Test** - Tests adding a new user in the Admin module
3. **Delete User Test** - Tests deleting a user from the Admin module
4. **Complete User Management Flow** - End-to-end test covering add, search, and delete user operations

## 🏃 Running Tests

### Run all tests:
```bash
mvn test
```

### Run specific test class:
```bash
mvn test -Dtest=OrangeHRMTest
```

### Run with TestNG XML:
```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Run in IDE:
- Right-click on `testng.xml` → Run As → TestNG Suite
- Or right-click on `OrangeHRMTest.java` → Run As → TestNG Test

## ⚙️ Configuration

### Browser Configuration
Edit `OrangeHRMTest.java` to change browser:
```java
// For Firefox
WebDriverManager.firefoxdriver().setup();
driver = new FirefoxDriver();

// For Edge
WebDriverManager.edgedriver().setup();
driver = new EdgeDriver();
```

### TestNG Configuration
Edit `testng.xml` to modify:
- Parallel execution
- Thread count
- Test groups
- Test priorities

## 🎯 Features

- ✅ Page Object Model (POM) design pattern
- ✅ TestNG for test execution and reporting
- ✅ WebDriverManager for automatic driver management
- ✅ Comprehensive test coverage for OrangeHRM
- ✅ Maven project structure

## 📝 Notes

- The tests use the OrangeHRM demo site: `https://opensource-demo.orangehrmlive.com`
- Default credentials: Username: `Admin`, Password: `admin123`
- Employee names in the add user test may need to be adjusted based on available employees in the system
- Tests include proper waits and error handling
- WebDriverManager automatically downloads and manages ChromeDriver

## 📦 Dependencies

- **Selenium WebDriver** 4.15.0
- **TestNG** 7.8.0
- **WebDriverManager** 5.6.2 (for driver management)

## 📄 License

ISC

## 👤 Author

Mina Nagy QA Engineer

