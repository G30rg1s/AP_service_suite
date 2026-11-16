package suites.TS_API_USER_REGISTRATION;

import gr.automation.platform.utils.Base;
import org.testng.annotations.AfterTest;

public class BaseUserRegistration extends Base {

    @AfterTest(alwaysRun = true)
    public void afterTest() {

        utils.cleanup().cleanupRegisteredUsers();
    }
}