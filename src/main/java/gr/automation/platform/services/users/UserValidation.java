
package gr.automation.platform.services.users;

import gr.automation.platform.services.common.RestCommonValidations;
import gr.automation.platform.utils.Utils;
import org.testng.Assert;

import java.util.Properties;

public class UserValidation {

    private final Utils utils;

    public UserValidation(Utils utils) {
        this.utils = utils;
    }

    public RestCommonValidations common() {
        return utils.common();
    }

    //--------------------------VALIDATION METHODS--------------------------

    public UserValidation verifyRegisteredUser(Properties params) {

        Assert.assertNotNull(
                utils.context().getResponse().jsonPath().get("id"),
                "User ID should not be null"
        );

        Assert.assertEquals(
                utils.context().getResponse().jsonPath().getString("firstName"),
                params.getProperty("expectedFirstName")
        );

        Assert.assertEquals(
                utils.context().getResponse().jsonPath().getString("lastName"),
                params.getProperty("expectedLastName")
        );

        Assert.assertEquals(
                utils.context().getResponse().jsonPath().getString("email"),
                params.getProperty("expectedEmail")
        );

        Assert.assertEquals(
                utils.context().getResponse().jsonPath().getString("phone"),
                params.getProperty("expectedPhone")
        );

        Assert.assertEquals(
                utils.context().getResponse().jsonPath().getString("fatherName"),
                params.getProperty("expectedFatherName")
        );

        Assert.assertEquals(
                utils.context().getResponse().jsonPath().getString("motherName"),
                params.getProperty("expectedMotherName")
        );

        Assert.assertEquals(
                utils.context().getResponse().jsonPath().getInt("age"),
                Integer.parseInt(params.getProperty("expectedAge"))
        );

        return this;
    }
}
