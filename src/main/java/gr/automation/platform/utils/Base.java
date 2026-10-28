package gr.automation.platform.utils;

import gr.automation.platform.utils.config.EnvDataConfig;
import gr.automation.platform.utils.config.ResourcesConfig;
import gr.automation.platform.utils.config.TestDataConfig;

import gr.automation.platform.utils.database.DatabaseTunnel;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterSuite;

import java.util.Properties;

public class Base {

    protected EnvDataConfig envDataConfig;
    protected ResourcesConfig resourcesConfig;
    protected TestDataConfig testDataConfig;
    protected Utils utils;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {

        utils = new Utils();
        envDataConfig = new EnvDataConfig();
        resourcesConfig = new ResourcesConfig();
        testDataConfig = new TestDataConfig();

        System.out.println("==================================");
        System.out.println("AP SERVICE SUITE - STARTED");
        System.out.println("Environment: dev.properties");
        System.out.println("==================================");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {

        envDataConfig = new EnvDataConfig();
        resourcesConfig = new ResourcesConfig();
        testDataConfig = new TestDataConfig();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {

        System.out.println("==================================");
        System.out.println("AP SERVICE SUITE - FINISHED");
        System.out.println("==================================");
    }

    public Utils step(String description) {
        Reporter.log(description, true);
        return utils;
    }

    public Properties getTestCaseData() {
        return getTestCaseData(null);
    }

    public Properties getTestCaseData(Integer instance) {

        String className = getClass().getSimpleName();
        String packageName = getClass().getPackageName();

        String testSuite = getTestSuiteName(packageName);

        String testCaseId = instance == null
                ? className
                : className + "." + instance;

        String resourcePath =
                resourcesConfig.getTestDataFile(testSuite);

        return testDataConfig.getTestData(
                resourcePath,
                testCaseId
        );
    }

    protected String getTestSuiteName(String packageName) {

        for (String part : packageName.split("\\.")) {

            if (part.startsWith("TS_API_")) {
                return part;
            }
        }

        throw new IllegalStateException(
                "Test suite not found in package: " + packageName
        );
    }
}