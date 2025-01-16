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

## Git and GitHub
- Upload code to Github, so Jenkins can access it to run tests
- Distributed version control system
  - Not directly connected to server, have local copy of code
  - Git - Version control tool
  - GitHub - Hosting service for Git repositories

## Jenkins
- Popular CI tool that is open source and platform independent
- Allows continuous development, build, test, and deployment of codes
- Access on http://localhost:8080/
- Setup:
  - Download: https://www.jenkins.io/download/ (Windows)
  - Install plugins: 
    - Manage Jenkins > Plugins (look under Available plugins or Installed plugins on left)
      - Install Maven Integration Plugin
      - Install SonarQube Scanner for Jenkins
      - Install BrowserStack
  - Configure Tools:
    - Manage Jenkins > Tools > 
      - JDK installations
      - Maven installations
        - MAVEN_HOME: C:\Program Files\apache-maven-3.9.6
  - Configure Email:
    - Manage Jenkins > System
      - Email Notification AND Extended E-mail Notification (both):
        - SMTP server: smtp.gmail.com
        - Advanced
          - Username: abc@gmail.com
          - Password: password123
            - Must use App Password with 2FA: https://myaccount.google.com/ > Security > 2-Step Verification > App passwords
          - Use SSL
          - SMTP port: 465
      - Jenkins Location:
        - System Admin e-mail address (set a from: email name): Jenkins Admin <abc@gmail.com>
  - Add environment variables:
    - Manage Jenkins > System > Global properties > Environment variables (such as SELENIUM_HUB_IP)
  - Set credentials, secrets, etc:
    - Manage Jenkins > Credentials > (global) > +Add Credentials (Add GitHub login)
- Create Jenkins Job:
  - +New Item > Maven Project > Enter a name
    - Note: To edit below settings later: Click on 'project name' > Configure
    - General:
      - Discard old builds > Log Rotation > Max # of builds to keep: 25
    - Source Code Management:
      - Git
        - Repository URL: https://github.com/yingalice/ci-cd
        - Credentials (GitHub login, added during setup)
        - Branches to build: */main
    - Build Environment
      - Delete workspace before build starts
      - Add timestamps to Console Output
    - Build
      - Root POM: automation-talks-ci-cd/demo-ui/pom.xml
      - Goals and options: test
    - Build Settings
      - E-mail Notification > Send email for every unstable build
    - Post-Build Actions
      - Editable Email Notification
        - Project Recipient List: abc@gmail.com
        - Default Subject: [$BUILD_STATUS] - $PROJECT_NAME - Build# $BUILD_NUMBER
        - Default Content: $DEFAULT_CONTENT (defaults set in Manage Jenkins > System > Default Content)
        - Attachments: automation-talks-ci-cd/demo-ui/target/surefire-reports/emailable-report.html
        - Attach Build Log
        - Advanced Settings > Triggers > Always (to generate an email regardless of status)
        - Tip: Click (?) next to Content Token Reference, to see the list of $TOKENS
- Run Jenkins Job:
  - Start: Build Now
  - View: Click on Build #<n> > Console Output 
- SonarQube Cloud:
  - Static code analysis
  - Setup:
    - SonarQube Cloud: http://sonarcloud.io
      - Login with GitHub > My Projects > + Analyze New Project (add your repo)
      - \<Project\> > Administration > Analysis Method
        - Turn off Automatic Analysis
        - With Other CI Tools > Maven > Save the SONAR_TOKEN value
    - Jenins:
      - Manage Jenkins > System > SonarQube servers:
        - Name: SonarQube Cloud Jenkins
        - Server URL: https://sonarcloud.io
        - Server authentication token: Copy token created in https://sonarcloud.io/account/security
      - Manage Jenkins > Tools > SonarQube Scanner installations:
        - Name: sonarqube-scanner
        - Install automatically
      - Dashboard > \<Project\> > Configuration > Pre-Steps:
        - Invoke top-level Maven targets:
          - Goals: test-compile (to create binaries needed in next step)
          - Advanced > POM: automation-talks-ci-cd/demo-ui/pom.xml
        - Execute SonarQube Scanner
          - Analysis properties (get from SonarQube.io > \<Project\> > Information):
            sonar.projectKey=yingalice_ci-cd
            sonar.organization=yingalice
            sonar.sources=automation-talks-ci-cd/demo-ui/src  (folder with code to scan)
            sonar.java.binaries=automation-talks-ci-cd/demo-ui/target/test-classes  (folder with .class files)
## Selenium Grid
  - Distributed test execution - Run in different machines, browsers, OS at the same time
  - Download Selenium Server (Grid): https://www.selenium.dev/downloads/ (.jar file)
  - Components
    - Hub (1)
      - Server for nodes, centralized point where nodes are connected
      - Distributes cases to nodes based on desired capability
      - Start in command prompt: `java -jar selenium-server-4.27.0.jar hub`
        - Note the hub-ip.
    - Node (many)
      - Machine you want to run your cases on
      - Start in command prompt: `java -jar selenium-server-4.27.0.jar node --selenium-manager true --hub http://<hub-ip>:4444 -port <nnnn>` (port required if running multiple nodes on 1 machine)
      - If running in VirtualBox: Machine > Settings > Network > Bridged Adapter (not NAT)
        - VM gets its own IP on the network (192...), and not on the LAN of the host (10...)
    - Selenium code:
      - Set desired capabilities and launch RemoteWebDriver:
       ```
        ChromeOptions chromeOptions = new ChromeOptions();
        // options.setBinary("/snap/firefox/current/usr/lib/firefox/firefox");  // Xubuntu Firefox (snap)
        chromeOptions.setCapability("platformName", Platform.LINUX);
        driver = new RemoteWebDriver(new URI("http://<hub-ip>:4444").toURL(), chromeOptions);
        // throws MalformedURLException, URISyntaxException
      ```
    - View Hub Dashboard: http://localhost:4444/
## Browserstack
  - Cloud platform to execute cases on real devices (test on many OS/browsers)
  - Get username and access key: https://automate.browserstack.com/dashboard/
    - Jenkins:
      - Manage Jenkins > System > BrowserStack > Add BrowserStack Credentials
    - Add Windows environment variables:
        - BROWSERSTACK_USERNAME
        - BROWSERSTACK_ACCESS_KEY
  - Integration (https://github.com/browserstack/testng-browserstack):
    - Use BrowserStack SDK:
      - `pom.xml`:
        ```
        <properties>
          <browserstack.version>1.29.3</browserstack.version>
        </properties>

        <dependency>
          <groupId>com.browserstack</groupId>
          <artifactId>browserstack-java-sdk</artifactId>
          <version>${browserstack.version}</version>
          <scope>compile</scope>
        </dependency>

        <plugin>
          <artifactId>maven-dependency-plugin</artifactId>
          <executions>
            <execution>
              <id>getClasspathFilenames</id>
              <goals>
                <goal>properties</goal>
              </goals>
            </execution>
          </executions>
        </plugin>

        <profiles>
          <profile>
            <id>browserstack</id>
            <build>
              <plugins>
                <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-surefire-plugin</artifactId>
                  <version>${surefire.version}</version>
                  <configuration>
                    <suiteXmlFiles>
                      <suiteXmlFile>${config.file}</suiteXmlFile>
                    </suiteXmlFiles>
                    <argLine>
                      -javaagent:${com.browserstack:browserstack-java-sdk:jar}
                    </argLine>
                  </configuration>
                </plugin>
              </plugins>
            </build>
          </profile>
        </profiles>
        ```
    - `browserstack.yml`:
      - userName: ${BROWSERSTACK_USERNAME}
      - accessKey: ${BROWSERSTACK_ACCESS_KEY}
      - platforms:
        - Use capabilities generator to specify devices: https://www.browserstack.com/docs/automate/capabilities
  - Run:
    - Option 1: On BrowserStack: `mvn test -P browserstack`
    - Option 2: On BrowserStack via Jenkins: \<Project\> > Configure > Build > Goals and options: `test -P browserstack`
    - Option 3: On my computer: `mvn test` (In pom.xml, have another surefire section without javaagent, outside of a profile)
## Docker
  - Open platform to build, ship, and run distributed apps as portable, isolated processes on any infrastructure
  - Has extensive library of prebuilt containers on Docker Hub
  - Containers includes application, dependencies, OS, environment settings
  - DockerFile (text instructions) --> Docker Image (packaged product) --> Docker Hub (centralized location for images)
  - Solves issue of:
    - Works on my machine, but not yours
      - Solution: Keeps consistent computing environment (settings, database, properties, etc)
    - Running virtual machines takes too much memory (set, dedicated memory)
      - Solution: Only takes memory as necessary, shares kernel with other containers
  - Setup Grid: `https://github.com/SeleniumHQ/docker-selenium/tree/trunk?tab=readme-ov-file#hub-and-nodes`
    - Create network: `docker network create grid`
    - Create Hub: `docker run -d -p 4442-4444:4442-4444 --net grid --name selenium-hub selenium/hub:4.28.0-20250120`
    - Create Nodes:
      - Chrome: `docker run -d -p 7900:7900 --net grid -e SE_EVENT_BUS_HOST=selenium-hub --shm-size="2g" -e SE_EVENT_BUS_PUBLISH_PORT=4442 -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 --name selenium-node-chrome selenium/node-chrome:4.28.0-20250120`
      - Edge: `docker run -d -p 7901:7900 --net grid -e SE_EVENT_BUS_HOST=selenium-hub --shm-size="2g" -e SE_EVENT_BUS_PUBLISH_PORT=4442 -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 --name selenium-node-edge selenium/node-edge:4.28.0-20250120`
      - Firefox: `docker run -d -p 7902:7900 --net grid -e SE_EVENT_BUS_HOST=selenium-hub --shm-size="2g" -e SE_EVENT_BUS_PUBLISH_PORT=4442 -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 --name selenium-node-firefox selenium/node-firefox:4.28.0-20250120`
      - Notes:
        - `-e SE_EVENT_BUS_HOST=<ip-from-hub-machine>`
        - `-e SE_NODE_HOST=<ip-from-this-nodes-machine>`
    - View container in browser (password is secret):
      - Chrome: http://localhost:7900/
      - Edge: http://localhost:7901/
      - Firefox: http://localhost:7902/
    - Run as usual using `mvn test` on your grid/RemoteWebDriver setup
    - Cleanup:
      - When done, and containers have been removed, then remove the grid network: `docker network rm grid`
  - Commands
    - `docker login` - Authenticate to Docker Hub (or other registry)
    - `docker pull <user>/<image>` - Downloads image from Docker Hub
    - `docker push <user>/<image>` - Uploads image to Docker Hub
    - `docker ps` - List running containers.  Add `-a` to get all containers.
    - `docker images` - Lists all images on local machine
    - `docker history <user>/<image>` - Lists history of an image
    - `docker rmi <img-id>` - Delete image
    - `doker run -it <user/image>` - Run image, create container, change to terminal inside container
    - `docker start <container-id>` - Start a container
    - `docker stop <container-id>` - Stop a container
    - `docker rm -f <container-id>` - Delete container
    - `docker exec <container-id> <shell cmd>` - Execute command within running container
    - `docker logs <container-id>` - Displays logs from a running container
    - `docker diff <container-id>` - Lists changes made to container
    - `docker port <container-id>` - Displays exposed port of running container