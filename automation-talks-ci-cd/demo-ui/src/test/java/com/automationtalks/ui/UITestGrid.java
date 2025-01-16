package com.automationtalks.ui;

import java.net.URI;
import java.time.Duration;
import java.util.logging.Logger;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UITestGrid {
  public static WebDriver driver;
  public static final String SELENIUM_HUB_IP = System.getenv("SELENIUM_HUB_IP");
  Logger logger = Logger.getLogger(getClass().getName());

  @BeforeMethod
  public static void launchDriver() throws Exception {
    ChromeOptions options = new ChromeOptions();
    // options.setBinary("/snap/firefox/current/usr/lib/firefox/firefox");  // Xubuntu Firefox (snap), doesn't automatically manage drivers
    options.setCapability("platformName", Platform.LINUX);

    driver = new RemoteWebDriver(new URI(SELENIUM_HUB_IP + ":4444").toURL(), options);  // grid hub ip
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    // driver.manage().window().fullscreen();
    driver.get("https://automationtalks.com/");
  }

  @Test
  public void test1() {
    logger.info("[GRID] Test 1 title is: " + driver.getTitle());
    Assert.assertEquals(driver.getTitle(), "AutomationTalks - Learn Automation Testing");
  }

  @Test
  public void test2() {
    logger.info("[GRID] Test 2 title is: " + driver.getTitle());
    Assert.assertTrue(driver.getTitle().matches("^AutomationTalks - Learn Automation Testing$"));
  }

  @Test
  public void test3() {
    logger.info("[GRID] Test 3 title is: " + driver.getTitle());
    Assert.assertTrue(driver.getTitle().matches("^AutomationTalks - .*"));
  }

  @AfterMethod
  public void quit() {
    driver.quit();
    // if test case fails then log the defect in JIRA
  }
}
