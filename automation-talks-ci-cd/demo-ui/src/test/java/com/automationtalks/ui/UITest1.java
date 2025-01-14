package com.automationtalks.ui;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.logging.Logger;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
// import org.openqa.selenium.edge.EdgeOptions;
// import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UITest1 {
  private static WebDriver driver;
  private static String url = "https://automationtalks.com/";
  Logger logger = Logger.getLogger(getClass().getName());
  
  @BeforeMethod
  public static void launchDriver() throws MalformedURLException, URISyntaxException {
    ChromeOptions options = new ChromeOptions();
    // options.setBinary("/snap/firefox/current/usr/lib/firefox/firefox");  // Xubuntu Firefox (snap)
    options.setCapability("platformName", Platform.LINUX);

    // driver = new ChromeDriver();  // non-grid
    driver = new RemoteWebDriver(new URI("http://192.168.0.208:4444").toURL(), options);  // grid
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().window().fullscreen();
  }

  @Test
  public void test1() {
    driver.get(url);
    logger.info("Test 1 title is: " + driver.getTitle());
    Assert.assertEquals(driver.getTitle(), "AutomationTalks - Learn Automation Testing");
  }

  @Test
  public void test2() {
    driver.get(url);
    logger.info("Test 2 title is: " + driver.getTitle());
    Assert.assertTrue(driver.getTitle().matches("^AutomationTalks - Learn Automation Testing$"));
  }

  @Test
  public void test3() {
    driver.get(url);
    logger.info("Test 3 title is: " + driver.getTitle());
    Assert.assertTrue(driver.getTitle().matches("^AutomationTalks - .*"));
  }

  @AfterMethod
  public void quit() {
    driver.quit();
    // if test case fails then log the defect in JIRA
  }
}
