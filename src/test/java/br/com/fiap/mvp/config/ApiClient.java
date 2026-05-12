package br.com.fiap.mvp.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class ApiClient {

    public static final String CITIZEN_ID_HEADER = "X-Citizen-Id";
    public static final String API_BASE_PATH = "/api/v1";

    private ApiClient() {
    }

    public static RequestSpecification request() {
        return new RequestSpecBuilder()
                .setBaseUri(TestConfig.baseUrl())
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification apiRequest() {
        return apiRequestWithCitizenId(TestConfig.citizenId());
    }

    public static RequestSpecification apiRequestWithCitizenId(String citizenId) {
        return new RequestSpecBuilder()
                .addRequestSpecification(request())
                .addHeader(CITIZEN_ID_HEADER, citizenId)
                .build();
    }

    public static RequestSpecification apiRequestWithoutCitizenId() {
        return request();
    }
}
