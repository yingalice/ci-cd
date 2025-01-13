package com.automationtalks.ui;

import java.time.Duration;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UITest1 {
  private static WebDriver driver;
  private static String url = "https://automationtalks.com/";
  Logger logger = Logger.getLogger(getClass().getName());
  
  @BeforeMethod
  public static void launchDriver() {
    driver = new ChromeDriver();
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
