package commons.web;

import commons.properties.PropertiesManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The WebDriverInitializer class is responsible for initializing a WebDriver instance based on the browser specified
 * in the configuration properties file. It uses the PropertiesManager class to retrieve configuration properties.
 *
 * The class provides a public method initializeWebDriver() to launch/start the web browser window and the WebDriver
 * session. The method returns the WebDriver instance created.
 *
 * @author Jaspal Aujla
 */
public class WebDriverInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverInitializer.class);
    private final PropertiesManager propertiesManager;

    /**
     * Constructs a new WebDriverInitializer.
     *
     * @param propertiesManager the properties manager to use for configuring the WebDriver
     */
    protected WebDriverInitializer(PropertiesManager propertiesManager) {
        LOGGER.info("Constructing WebDriverInitializer with the specified properties manager");
        this.propertiesManager = propertiesManager;
    }

    /**
     * Launch/start web browser window and WebDriver session.
     *
     * @return The WebDriver instance created
     */
    protected WebDriver initializeWebDriver() {
        LOGGER.info("Initializing WebDriver");
        String browserName = System.getProperty("browser.name", propertiesManager.getProperty("web.browser.name").toLowerCase());
        String headless = System.getProperty("headless", propertiesManager.getProperty("headless"));

        WebDriver driver;
        switch (browserName) {
            case "chrome":
                driver = initializeChromeDriver(headless);
                break;
            case "firefox":
                driver = initializeFirefoxDriver(headless);
                break;
            case "edge":
                driver = initializeEdgeDriver(headless);
                break;
            case "safari":
                driver = initializeSafariDriver();
                break;
            default:
                LOGGER.error("Value of 'web.browser' in 'config properties' file should be: chrome, firefox, edge or safari. Unsupported browser: " + browserName);
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        LOGGER.info("Web browser '" + browserName + "' launched successfully");
        return driver;
    }

    /**
     * Initializes the ChromeDriver with ChromeOptions.
     *
     * @param headless Whether to run Chrome in headless mode (true) or not (false)
     * @return The ChromeDriver instance
     */
    private WebDriver initializeChromeDriver(String headless) {
        ChromeOptions options = new ChromeOptions();
        if ("true".equalsIgnoreCase(headless)) {
            options.addArguments("--headless=new");
        }
        return new ChromeDriver(options);
    }

    /**
     * Initializes the FirefoxDriver with FirefoxOptions.
     *
     * @param headless Whether to run Firefox in headless mode (true) or not (false)
     * @return The FirefoxDriver instance
     */
    private WebDriver initializeFirefoxDriver(String headless) {
        FirefoxOptions options = new FirefoxOptions();
        if ("true".equalsIgnoreCase(headless)) {
            options.addArguments("-headless");
        }
        return new FirefoxDriver(options);
    }

    /**
     * Initializes the EdgeDriver with EdgeOptions.
     *
     * @param headless Whether to run Edge in headless mode (true) or not (false)
     * @return The EdgeDriver instance
     */
    private WebDriver initializeEdgeDriver(String headless) {
        EdgeOptions options = new EdgeOptions();
        if ("true".equalsIgnoreCase(headless)) {
            options.addArguments("--headless=new");
        }
        return new EdgeDriver(options);
    }

    /**
     * Initializes the SafariDriver.
     *
     * @return The SafariDriver instance
     */
    private WebDriver initializeSafariDriver() {
        try {
            return new SafariDriver();
        } catch (Throwable e) {
            LOGGER.error("Failed to initialize SafariDriver", e);
            throw e;
        }
    }
}
