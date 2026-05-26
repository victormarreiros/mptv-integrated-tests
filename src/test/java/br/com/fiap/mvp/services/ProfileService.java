package br.com.fiap.mvp.services;

import br.com.fiap.mvp.config.ApiClient;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProfileService {

    private static final String PROFILE_ME_PATH = ApiClient.API_BASE_PATH + "/profile/me";

    public Response getProfile() {
        return given()
                .spec(ApiClient.apiRequest())
                .when()
                .get(PROFILE_ME_PATH);
    }

    public Response getProfileWithCitizenId(String citizenId) {
        return given()
                .spec(ApiClient.apiRequestWithCitizenId(citizenId))
                .when()
                .get(PROFILE_ME_PATH);
    }

    public Response getProfileWithoutCitizenId() {
        return given()
                .spec(ApiClient.apiRequestWithoutCitizenId())
                .when()
                .get(PROFILE_ME_PATH);
    }
}
