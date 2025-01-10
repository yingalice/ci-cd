package com.automationtalks.ui;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UITest1 {
  public static WebDriver driver;

  @BeforeMethod
  public void launchDriver() {
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().window().fullscreen();
  }

  @Test
  public void Test1() {
    driver.get("https://automationtalks.com/");
    Assert.assertEquals(driver.getTitle(), "AutomationTalks - Learn Automation Testing");
    System.out.println("Test 1 title is: " + driver.getTitle());
  }

  @Test
  public void Test2() {
    driver.get("https://automationtalks.com/");
    Assert.assertTrue(driver.getTitle().matches("^AutomationTalks - Learn Automation Testing5$"));
    System.out.println("Test 2 title is: " + driver.getTitle());
  }

  @Test
  public void Test3() {
    driver.get("https://automationtalks.com/");
    Assert.assertTrue(driver.getTitle().matches("^AutomationTalks - .*"));
    System.out.println("Test 3 title is: " + driver.getTitle());
  }

  @AfterMethod
  public void quit() {
    driver.quit();
    // if test case fails then log the defect in JIRA
  }
}
