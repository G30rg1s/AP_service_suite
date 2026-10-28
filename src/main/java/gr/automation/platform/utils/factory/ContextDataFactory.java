package gr.automation.platform.utils.factory;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ContextDataFactory {

    private ExtractableResponse<Response> response;
    private String token;
    private String userId;
    private String documentId;

    public ExtractableResponse<Response> getResponse() {
        return response;
    }

    public void setResponse(ExtractableResponse<Response> response) {
        this.response = response;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}