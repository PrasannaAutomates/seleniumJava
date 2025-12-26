# Automation Framework

This is a comprehensive Java-based automation testing framework using Selenium WebDriver, TestNG, Page Object Model with PageFactory, data-driven testing, SQL Server database integration, screenshot capture on failures, and logging.

## Features

- **Page Object Model (POM)**: Organized page classes with PageFactory for element initialization and explicit waits.
- **Data-Driven Testing**: Test data read from Excel files using Apache POI.
- **Database Integration**: Utilities for connecting to SQL Server, with query and insert operations.
- **Screenshot Capture**: Automatic screenshots on test failures.
- **Logging**: SLF4J logging for better traceability.
- **TestNG**: For test execution, data providers, and reporting.
- **Configuration Management**: Properties file for configurable settings.
- **Cross-Browser Support**: Chrome and Firefox.

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- SQL Server instance (for DB tests)
- Chrome or Firefox browser

## Setup

1. Clone or download the project.
2. Update `src/test/resources/config.properties` with your database URL, credentials, and browser preference.
3. Create an Excel file `src/test/resources/testdata.xlsx` with a sheet named "Login" containing columns: username, password. Example data:
   - Row 1: tomsmith, SuperSecretPassword! (valid)
   - Row 2: invaliduser, invalidpass (invalid)
4. Ensure SQL Server is running and has a table `users` with columns `username` and `password` (varchar types).
5. For DB insert test, ensure the table allows inserts.

## Running Tests

- To run all tests: `mvn clean test`
- To run specific test suite: `mvn clean test -DsuiteXmlFile=testng.xml`
- Screenshots will be saved in `screenshots/` directory on failures.

## Project Structure

- `src/main/java/com/automation/framework/pages/`: Page classes (LoginPage, HomePage, BasePage)
- `src/main/java/com/automation/framework/utils/`: Utility classes (DBUtils, ExcelUtils, ConfigReader)
- `src/test/java/com/automation/framework/tests/`: Test classes (TestBase, LoginTest, DBTest)
- `src/test/resources/`: Configuration and test data files
- `screenshots/`: Directory for failure screenshots (auto-created)

## Dependencies

- Selenium WebDriver 4.15.0
- TestNG 7.8.0
- Microsoft SQL Server JDBC Driver 12.4.1
- Apache POI 5.2.4
- WebDriverManager 5.5.3
- Commons IO 2.11.0
- SLF4J 2.0.9

## Dummy Website

The framework uses [The Internet](https://the-internet.herokuapp.com/) as a dummy website for UI testing, specifically the login page for demonstration.

## Logging

Logs are output to console. For file logging, configure logback or similar.

## Extending the Framework

- Add more page classes in `pages/` package.
- Create new test classes extending `TestBase`.
- Update `testng.xml` for new test suites.
- Add more utilities as needed.