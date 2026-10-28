package gr.automation.platform.utils.config;

import java.io.InputStream;

public class ResourcesConfig {

    private static final String ENVIRONMENT_DIR = "environment/";
    private static final String TEST_DATA_DIR = "test-data/";
    private static final String TEST_SUITES_DIR = "test-suites/";

    public String getEnvironmentProperties() {
        return ENVIRONMENT_DIR + "dev.properties";
    }

    public String getTestDataDir() {
        return TEST_DATA_DIR;
    }

    public String getTestSuitesDir() {
        return TEST_SUITES_DIR;
    }

    public String getTestDataFile(String suiteName) {
        return TEST_DATA_DIR + suiteName + ".properties";
    }

    public InputStream getResourceAsStream(String resourcePath) {
        InputStream input = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourcePath);

        if (input == null) {
            throw new IllegalArgumentException(
                    "Resource not found: " + resourcePath
            );
        }

        return input;
    }
}