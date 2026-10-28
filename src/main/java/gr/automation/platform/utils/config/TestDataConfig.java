package gr.automation.platform.utils.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.InputStreamReader;
import java.util.Properties;

public class TestDataConfig {

    private final ResourcesConfig resourcesConfig;

    public TestDataConfig() {
        resourcesConfig = new ResourcesConfig();
    }

    public Properties getTestData(String resourcePath, String testCaseId) {

        Properties allProperties = new Properties();
        Properties testCaseProperties = new Properties();

        try (InputStream input =
                     resourcesConfig.getResourceAsStream(resourcePath);

             InputStreamReader reader =
                     new InputStreamReader(input, StandardCharsets.UTF_8)) {

            allProperties.load(reader);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to load test data: " + resourcePath, e
            );
        }

        String prefix = testCaseId + ".";

        for (String key : allProperties.stringPropertyNames()) {

            if (key.startsWith(prefix)) {

                String propertyName = key.substring(prefix.length());
                String propertyValue = allProperties.getProperty(key);

                testCaseProperties.setProperty(
                        propertyName,
                        propertyValue
                );
            }
        }

        if (testCaseProperties.isEmpty()) {
            throw new IllegalArgumentException(
                    "No test data found for: " + testCaseId
                            + " in " + resourcePath
            );
        }

        return testCaseProperties;
    }
}