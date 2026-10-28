
package gr.automation.platform.utils;

import gr.automation.platform.services.common.RestCommonValidations;
import gr.automation.platform.utils.common.Cleanup;
import gr.automation.platform.utils.common.Logger;
import gr.automation.platform.utils.config.EnvDataConfig;
import gr.automation.platform.utils.factory.ContextDataFactory;
import gr.automation.platform.utils.factory.ServiceObjectFactory;

public class Utils {

    private final EnvDataConfig environment;
    private final ContextDataFactory contextFactory;
    private final ServiceObjectFactory serviceObjectFactory;
    private final Logger logger;
    private final RestCommonValidations commonValidations;
    private final Cleanup cleanup;

    public Utils() {
        environment = new EnvDataConfig();
        contextFactory = new ContextDataFactory();
        serviceObjectFactory = new ServiceObjectFactory(this);
        commonValidations = new RestCommonValidations(this);
        logger = new Logger();
        cleanup = new Cleanup(this);
    }

    public RestCommonValidations common() {
        return commonValidations;
    }

    // ========================================
    // SERVICE OBJECT FACTORY
    // ========================================

    public ServiceObjectFactory service() {
        return serviceObjectFactory;
    }

    // ========================================
    // CONTEXT DATA FACTORY
    // ========================================

    public ContextDataFactory context() {
        return contextFactory;
    }

    // ========================================
    // ENVIRONMENT CONFIG
    // ========================================

    public EnvDataConfig environment() {
        return environment;
    }

    // ========================================
    // LOGGER
    // ========================================

    public Logger logger() {
        return logger;
    }

    // ========================================
    // CLEANUP
    // ========================================

    public Cleanup cleanup() {
        return cleanup;
    }

    // ========================================
    // TIMEOUTS
    // ========================================

    public long getTimeoutMillis() {
        return environment.getTimeout();
    }

    public long getIntervalMillis() {
        return environment.getInterval();
    }

    // ========================================
// DATABASE
// ========================================

    public String getDatabaseTarget() {
        return environment.getDatabaseTarget();
    }

    public String getDbUrl() {
        return environment.getDbUrl();
    }

    public String getDbUsername() {
        return environment.getDbUsername();
    }

    public String getDbPassword() {
        return environment.getDbPassword();
    }
}
