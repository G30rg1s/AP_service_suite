package suites.TS_API_USER_REGISTRATION.TS_API_USER_REGISTRATION;

import gr.automation.platform.utils.listeners.CustomTestNGListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(CustomTestNGListener.class)
public class AP_SVC_USER_REGISTRATION_0001 extends BaseUserRegistration {

    @Test
    public void AP_SVC_USER_REGISTRATION_0001() {

        step("Verify User Registration API")
                .service().userRest().register(getTestCaseData(1))
                          .validate.common().verifyStatusCode(201);
        utils.service().userRest().validate.verifyRegisteredUser(getTestCaseData(2));
    }
}
