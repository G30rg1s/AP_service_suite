package gr.automation.platform.utils.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvDataConfig {

    private static final String CONFIG_FILE = "environment/dev.properties";

    private final Properties properties = new Properties();

    public EnvDataConfig() {
        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            if (input == null) {
                throw new IllegalStateException(
                        "Configuration file not found: " + CONFIG_FILE
                );
            }

            properties.load(input);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to load configuration: " + CONFIG_FILE, e
            );
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);

        if (value == null) {
            throw new IllegalArgumentException(
                    "Missing environment property: " + key
            );
        }

        return value.trim();
    }

    public String getAppUrl() {
        return getProperty("app.url");
    }

    public String getBasePath() {
        return getProperty("app.base.path");
    }

    public String getEndpoint(String key) {
        return getProperty(key);
    }

    public int getTimeout() {
        return Integer.parseInt(getProperty("timeout"));
    }

    public int getInterval() {
        return Integer.parseInt(getProperty("interval"));
    }

    public String getDatabaseTarget() {return getProperty("database.target");}

    // ========================================
    // DATABASE - JDBC
    // ========================================

    public String getDbUrl() {return getProperty("db.url");}

    public String getDbUsername() {return getProperty("db.username");}

    public String getDbPassword() {return getProperty("db.password");}

}