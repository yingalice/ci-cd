package com.automationtalks.ui;

import java.net.URI;
import java.time.Duration;
import java.util.logging.Logger;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class UITest {
  public WebDriver driver;
  public static final String SELENIUM_HUB_IP = System.getenv("SELENIUM_HUB_IP");
  Logger logger = Logger.getLogger(getClass().getName());
  
  @BeforeMethod
  @Parameters({"browser", "platformName", "useGrid"})
  public void launchDriver(String browser, String platformName, boolean useGrid) throws Exception {
    MutableCapabilities options;
    Platform platform;
    
    options = switch (browser.toLowerCase()) {
      case "chrome" -> new ChromeOptions();
      case "firefox" -> new FirefoxOptions();
      case "edge" -> new EdgeOptions();
      default -> {
        System.out.println("Invalid browser: " + browser);
        System.out.println("Defaulting to Chrome...");
        yield new ChromeOptions();
      }
    };

    if (useGrid) {  // hub
      platform = switch (platformName.toLowerCase()) {
        case "windows" -> Platform.WINDOWS;
        case "linux" -> {
          if (browser.equalsIgnoreCase("firefox")) {
            ((FirefoxOptions)options).setBinary("/snap/firefox/current/usr/lib/firefox/firefox");  // Xubuntu Firefox (snap), doesn't automatically manage drivers
          };
          yield Platform.LINUX;
        }
        default -> {
          System.out.println("Invalid platform: " + platformName);
          System.out.println("Defaulting to Windows...");
          yield Platform.WINDOWS;
        }
      };
      options.setCapability("platformName", platform);
      driver = new RemoteWebDriver(new URI(SELENIUM_HUB_IP + ":4444").toURL(), options);
    } else {  // non-hub
      driver = switch (browser.toLowerCase()) {
        case "chrome" -> new ChromeDriver((ChromeOptions)options);
        case "firefox" -> new FirefoxDriver((FirefoxOptions)options);
        case "edge" -> new EdgeDriver((EdgeOptions)options);
        default -> {
          System.out.println("Invalid browser: " + browser);
          System.out.println("Defaulting to Chrome...");
          yield new ChromeDriver((ChromeOptions)options);
        }
      };
    };
    
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    // driver.manage().window().maximize();
    driver.get("https://automationtalks.com/");
  }

  @Test
  public void test1() {
    logger.info("Test 1 title is: " + driver.getTitle());
    Assert.assertEquals(driver.getTitle(), "AutomationTalks - Learn Automation Testing");
  }

  @Test
  public void test2() {
    logger.info("Test 2 title is: " + driver.getTitle());
    Assert.assertTrue(driver.getTitle().matches("^AutomationTalks - Learn Automation Testing$"));
  }

  @Test
  public void test3() {
    logger.info("Test 3 title is: " + driver.getTitle());
    Assert.assertTrue(driver.getTitle().matches("^AutomationTalks - .*"));
  }

  @AfterMethod
  public void quit() {
    driver.quit();
    // if test case fails then log the defect in JIRA
  }
}
