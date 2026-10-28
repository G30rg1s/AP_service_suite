
package gr.automation.platform.services.common;

import gr.automation.platform.utils.Utils;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.Objects;

public class RestCommonValidations {

    protected final Utils utils;

    public RestCommonValidations(Utils utils) {
        this.utils = utils;
    }

    protected ExtractableResponse<Response> response() {

        ExtractableResponse<Response> response =
                utils.context().getResponse();

        if (response == null) {
            throw new IllegalStateException(
                    "No API response available for validation"
            );
        }

        return response;
    }

    // ========================================
    // STATUS CODE
    // ========================================

    public RestCommonValidations verifyStatusCode(int expectedStatusCode) {

        Assert.assertEquals(
                response().statusCode(),
                expectedStatusCode,
                "Unexpected HTTP status code"
        );

        return this;
    }

    // ========================================
    // RESPONSE BODY
    // ========================================

    public RestCommonValidations verifyResponseIsEmpty() {

        Assert.assertTrue(
                response().asString().isEmpty(),
                "Expected empty response body"
        );

        return this;
    }

    public RestCommonValidations verifyResponseContains(String expectedText) {

        Assert.assertTrue(
                response().asString().contains(expectedText),
                "Response does not contain: " + expectedText
        );

        return this;
    }

    public RestCommonValidations verifyResponseDoesNotContain(String text) {

        Assert.assertFalse(
                response().asString().contains(text),
                "Response contains unexpected text: " + text
        );

        return this;
    }

    // ========================================
    // JSON FIELDS
    // ========================================

    public RestCommonValidations verifyFieldEquals(
            String jsonPath,
            Object expectedValue) {

        Assert.assertEquals(
                response().path(jsonPath),
                expectedValue,
                "Incorrect value for: " + jsonPath
        );

        return this;
    }

    public RestCommonValidations verifyFieldNotNull(String jsonPath) {

        Assert.assertNotNull(
                response().path(jsonPath),
                "Field is null or missing: " + jsonPath
        );

        return this;
    }

    public RestCommonValidations verifyFieldIsNull(String jsonPath) {

        Assert.assertNull(
                response().path(jsonPath),
                "Expected null field: " + jsonPath
        );

        return this;
    }

    // ========================================
    // JSON LISTS
    // ========================================

    public RestCommonValidations verifyResponseListIsEmpty() {

        List<?> values = response().jsonPath().getList("$");

        Assert.assertNotNull(values, "Response list is null");
        Assert.assertTrue(values.isEmpty(), "Response list is not empty");

        return this;
    }

    public RestCommonValidations verifyListContains(
            String jsonPath,
            Object expectedValue) {

        List<?> values = response().jsonPath().getList(jsonPath);

        Assert.assertNotNull(values, "List not found: " + jsonPath);

        Assert.assertTrue(
                values.stream().anyMatch(
                        value -> Objects.equals(value, expectedValue)
                ),
                "Expected value not found: " + expectedValue
        );

        return this;
    }

    public RestCommonValidations verifyListDoesNotContain(
            String jsonPath,
            Object forbiddenValue) {

        List<?> values = response().jsonPath().getList(jsonPath);

        Assert.assertNotNull(values, "List not found: " + jsonPath);

        Assert.assertFalse(
                values.contains(forbiddenValue),
                "Unexpected value found: " + forbiddenValue
        );

        return this;
    }

    public RestCommonValidations verifyListSize(
            String jsonPath,
            int expectedSize) {

        List<?> values = response().jsonPath().getList(jsonPath);

        Assert.assertNotNull(values, "List not found: " + jsonPath);

        Assert.assertEquals(
                values.size(),
                expectedSize,
                "Incorrect list size: " + jsonPath
        );

        return this;
    }

    // ========================================
    // RESPONSE HEADERS
    // ========================================

    public RestCommonValidations verifyHeaderEquals(
            String headerName,
            String expectedValue) {

        Assert.assertEquals(
                response().header(headerName),
                expectedValue,
                "Incorrect response header: " + headerName
        );

        return this;
    }
}
