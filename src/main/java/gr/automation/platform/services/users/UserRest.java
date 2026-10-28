
package gr.automation.platform.services.users;

import gr.automation.platform.services.common.RestCommonRequests;
import gr.automation.platform.services.users.pojos.UserLoginRequest;
import gr.automation.platform.services.users.pojos.UserRegisterRequest;
import gr.automation.platform.services.users.pojos.UserUpdateRequest;
import gr.automation.platform.utils.Utils;
import gr.automation.platform.utils.factory.StaticContextDataFactory;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UserRest {

    private final Utils utils;
    private final RestCommonRequests requests;
    private ExtractableResponse<Response> response;
    public final UserValidation validate;

    public UserRest(Utils utils) {
        this.utils = utils;
        this.requests = new RestCommonRequests(utils);
        this.validate = new UserValidation(utils);
    }

    // ========================================
    // USER REGISTRATION
    // POST /cf_automation/users/register
    // ========================================

    public UserRest register(Properties params) {

        UserRegisterRequest requestBody = prepareRegisterBody(params);

        response = requests.post(UserEndpoints.REGISTER.getPath(), requestBody);
        utils.context().setResponse(response);

        if (response.statusCode() == 201) {
            String email = response.jsonPath().getString("email");
            StaticContextDataFactory.userEmailList.add(email);
        }

        return this;
    }

    // ========================================
    // USER LOGIN
    // POST /cf_automation/users/login
    // ========================================

    public UserRest login(Properties params) {

        UserLoginRequest requestBody = prepareLoginBody(params);

        response = requests.post(UserEndpoints.LOGIN.getPath(), requestBody);

        utils.context().setResponse(response);

        if (response.statusCode() == 200) {

            String token = response.jsonPath().getString("token");

            utils.context().setToken(token);
            StaticContextDataFactory.token = token;
        }

        return this;
    }

    // ========================================
    // USER PROFILE UPDATE
    // PUT /cf_automation/users/update
    // ========================================

    public UserRest update(Properties params) {

        UserUpdateRequest requestBody = prepareUpdateBody(params);

        response = requests.put(UserEndpoints.UPDATE.getPath(), requestBody);
        utils.context().setResponse(response);

        return this;
    }

    // ========================================
    // PREPARE REGISTRATION REQUEST BODY
    // ========================================

    private UserRegisterRequest prepareRegisterBody(Properties params) {

        UserRegisterRequest request = new UserRegisterRequest();

        request.setFirstName(params.getProperty("firstName"));
        request.setLastName(params.getProperty("lastName"));
        request.setEmail(params.getProperty("email"));
        request.setPhone(params.getProperty("phone"));
        request.setFatherName(params.getProperty("fatherName"));
        request.setMotherName(params.getProperty("motherName"));

        String age = params.getProperty("age");

        if (age != null && !age.isBlank()) {
            request.setAge(Integer.parseInt(age));
        }

        request.setPassword(params.getProperty("password"));

        return request;
    }

    // ========================================
    // PREPARE LOGIN REQUEST BODY
    // ========================================

    private UserLoginRequest prepareLoginBody(Properties params) {

        UserLoginRequest request = new UserLoginRequest();

        request.setEmail(params.getProperty("Email"));
        request.setPassword(params.getProperty("Password"));

        return request;
    }

    // ========================================
    // PREPARE UPDATE REQUEST BODY
    // ========================================

    private UserUpdateRequest prepareUpdateBody(Properties params) {

        UserUpdateRequest request = new UserUpdateRequest();

        request.setFirstName(params.getProperty("FirstName"));
        request.setLastName(params.getProperty("LastName"));
        request.setEmail(params.getProperty("Email"));
        request.setPhone(params.getProperty("Phone"));
        request.setFatherName(params.getProperty("FatherName"));
        request.setMotherName(params.getProperty("MotherName"));

        String age = params.getProperty("Age");

        if (age != null && !age.isBlank()) {
            request.setAge(Integer.parseInt(age));
        }

        return request;
    }
}
