
package gr.automation.platform.services.common;

import gr.automation.platform.utils.Utils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.Map;

public class RestCommonRequests {

    private final Utils utils;

    public RestCommonRequests(Utils utils) {
        this.utils = utils;
    }

    // ========================================
    // COMMON REQUEST SPECIFICATION
    // ========================================

    private RequestSpecification request() {

        RequestSpecification spec = RestAssured.given()
                .baseUri(utils.environment().getAppUrl())
                .basePath(utils.environment().getBasePath())
                .accept(ContentType.JSON)
                .header("X-Database-Target", utils.getDatabaseTarget());

        String token = utils.context().getToken();

        if (token != null && !token.isBlank()) {
            spec.header("Authorization", "Bearer " + token);
        }

        return spec
                .log()
                .all();
    }

    private ExtractableResponse<Response> extract(Response response) {

        ExtractableResponse<Response> extracted = response
                .then()
                .log()
                .all()
                .extract();

        utils.context().setResponse(extracted);

        return extracted;
    }

    // ========================================
    // GET
    // ========================================

    public ExtractableResponse<Response> get(String endpoint) {

        return extract(
                request()
                        .when()
                        .get(endpoint)
        );
    }

    public ExtractableResponse<Response> get(
            String endpoint,
            Map<String, ?> queryParams) {

        return extract(
                request()
                        .queryParams(queryParams)
                        .when()
                        .get(endpoint)
        );
    }

    public ExtractableResponse<Response> getWithPathParams(
            String endpoint,
            Map<String, ?> pathParams) {

        return extract(
                request()
                        .pathParams(pathParams)
                        .when()
                        .get(endpoint)
        );
    }

    // ========================================
    // POST
    // ========================================

    public ExtractableResponse<Response> post(String endpoint, Object requestBody) {

        return extract(
                request()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .post(endpoint)
        );
    }

    public ExtractableResponse<Response> post(String endpoint) {

        return extract(
                request()
                        .when()
                        .post(endpoint)
        );
    }

    public ExtractableResponse<Response> postWithQueryParams(
            String endpoint,
            Map<String, ?> queryParams,
            Object requestBody) {

        RequestSpecification spec = request()
                .queryParams(queryParams)
                .contentType(ContentType.JSON);

        if (requestBody != null) {
            spec.body(requestBody);
        }

        return extract(
                spec.when().post(endpoint)
        );
    }

    public ExtractableResponse<Response> postWithPathParams(
            String endpoint,
            Map<String, ?> pathParams,
            Object requestBody) {

        RequestSpecification spec = request()
                .pathParams(pathParams)
                .contentType(ContentType.JSON);

        if (requestBody != null) {
            spec.body(requestBody);
        }

        return extract(
                spec.when().post(endpoint)
        );
    }

    // ========================================
    // PUT
    // ========================================

    public ExtractableResponse<Response> put(
            String endpoint,
            Object requestBody) {

        return extract(
                request()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .put(endpoint)
        );
    }

    public ExtractableResponse<Response> putWithQueryParams(
            String endpoint,
            Map<String, ?> queryParams,
            Object requestBody) {

        return extract(
                request()
                        .queryParams(queryParams)
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .put(endpoint)
        );
    }

    // ========================================
    // PATCH
    // ========================================

    public ExtractableResponse<Response> patch(
            String endpoint,
            Object requestBody) {

        return extract(
                request()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .patch(endpoint)
        );
    }

    // ========================================
    // DELETE
    // ========================================

    public ExtractableResponse<Response> delete(String endpoint) {

        return extract(
                request()
                        .when()
                        .delete(endpoint)
        );
    }

    public ExtractableResponse<Response> deleteWithQueryParams(
            String endpoint,
            Map<String, ?> queryParams) {

        return extract(
                request()
                        .queryParams(queryParams)
                        .when()
                        .delete(endpoint)
        );
    }

    // ========================================
    // MULTIPART UPLOAD
    // ========================================

    public ExtractableResponse<Response> upload(
            String endpoint,
            String fieldName,
            File file) {

        return extract(
                request()
                        .multiPart(fieldName, file)
                        .when()
                        .post(endpoint)
        );
    }

    public ExtractableResponse<Response> uploadWithPathParams(
            String endpoint,
            Map<String, ?> pathParams,
            String fieldName,
            File file) {

        return extract(
                request()
                        .pathParams(pathParams)
                        .multiPart(fieldName, file)
                        .when()
                        .post(endpoint)
        );
    }

    // ========================================
    // CUSTOM REQUEST
    // ========================================

    public ExtractableResponse<Response> execute(
            String method,
            String endpoint,
            Map<String, ?> queryParams,
            Map<String, ?> pathParams,
            Map<String, String> headers,
            Object requestBody) {

        RequestSpecification spec = request();

        if (queryParams != null && !queryParams.isEmpty()) {
            spec.queryParams(queryParams);
        }

        if (pathParams != null && !pathParams.isEmpty()) {
            spec.pathParams(pathParams);
        }

        if (headers != null && !headers.isEmpty()) {
            spec.headers(headers);
        }

        if (requestBody != null) {
            spec.contentType(ContentType.JSON);
            spec.body(requestBody);
        }

        return extract(
                spec.when().request(method, endpoint)
        );
    }
}
