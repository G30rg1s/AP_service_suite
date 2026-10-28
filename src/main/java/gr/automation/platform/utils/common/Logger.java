
package gr.automation.platform.utils.common;

import org.slf4j.LoggerFactory;

public class Logger {

    private final org.slf4j.Logger logger;

    public Logger() {
        logger = LoggerFactory.getLogger(Logger.class);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void debug(String message) {
        logger.debug(message);
    }
}
