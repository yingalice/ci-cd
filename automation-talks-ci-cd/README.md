# Cypress - JavaScript End to End Testing(2022 Series)

## About
- Course: [CICD For Automation Testers](https://www.youtube.com/playlist?list=PL5fOKT7XR42Po4zh8nHCozrcsMboLNXjF)
- Instructor: [AutomationTalks](https://www.youtube.com/@AutomationTalks)

## CICD (Continuous Integration, Continuous Delivery/Deployment)
- DevOps is the neverending process of continuous improvement
  - Integration, development, testing, deployment, monitoring
  - CI: Plan, code (check in to repo), build automatically (maven), and test automatically (selenium)
  - CD: Release, deploy, operate

## Create Selenium/TestNG Maven project (Java)
- Install VSCode extension:
  - Extension Pack for Java
- Create a Maven project:
  - Command Palette (Ctrl+Shift+P) >  Java: Create Java Project > Maven > maven-archetype-quickstart > Follow prompts > "0.0.1-SNAPSHOT" for version
  - Maven: 
    - Build and dependency resolution tool for Java (like npm is for JS).  
    - Configure `pom.xml`.  Find dependencies in https://mvnrepository.com/
    - Need `selenium-java`, `testng`, and `maven-surefire-plugin`.  Specify TestNG XML paths in surefire section.
    ```
      <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
      </properties>

      <dependencies>
        <dependency>
          <groupId>org.seleniumhq.selenium</groupId>
          <artifactId>selenium-java</artifactId>
          <version>4.27.0</version>
        </dependency>
        <dependency>
          <groupId>org.testng</groupId>
          <artifactId>testng</artifactId>
          <version>7.10.2</version>
          <scope>test</scope>
        </dependency>
      </dependencies>

      <build>
        <pluginManagement>
          <plugins>
            <plugin>
              <groupId>org.apache.maven.plugins</groupId>
              <artifactId>maven-surefire-plugin</artifactId>
              <version>3.5.2</version>
              <configuration>
                <suiteXmlFiles>
                  <suiteXmlFile>TestNG.xml</suiteXmlFile>
                </suiteXmlFiles>
              </configuration>
            </plugin>
          </plugins>
        </pluginManagement>
      </build>
    ```
  - Structure:
    - /src/main/java: Common methods/utilities (non-test)
    - /src/test/java: Test cases
  - Create Seleneium test cases (under /src/test/java/com/automationtalks/ui/UITest1.java):
    ```
    @BeforeMethod
    public void launchDriver() {
      driver = new ChromeDriver();
      driver.manage().window().fullscreen();
    }

    @Test
    public void Test1() {
      driver.get("https://automationtalks.com/");
      Assert.assertEquals(driver.getTitle(), "AutomationTalks - Learn Automation Testing");
    }

    @AfterMethod
    public void quit() {
      driver.quit();
    }
    ```
  - Create TestNG XML (specify path in `pom.xml`):
    ```
    <!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd" >

    <suite name="Suite">
      <listeners>
        <listener class-name="org.uncommons.reportng.HTMLReporter" />
      </listeners>
      <test name="Test">
        <classes>
          <class name="com.automationtalks.ui.UITest1"/>
        </classes>
      </test>
    </suite> 
    ```
  - Run with `mvn test`