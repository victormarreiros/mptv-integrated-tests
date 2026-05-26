package br.com.fiap.mvp.services;

import br.com.fiap.mvp.config.ApiClient;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HomeService {

    private static final String HOME_ME_PATH = ApiClient.API_BASE_PATH + "/home/me";

    public Response getHome() {
        return given()
                .spec(ApiClient.apiRequest())
                .when()
                .get(HOME_ME_PATH);
    }

    public Response getHomeWithCitizenId(String citizenId) {
        return given()
                .spec(ApiClient.apiRequestWithCitizenId(citizenId))
                .when()
                .get(HOME_ME_PATH);
    }

    public Response getHomeWithoutCitizenId() {
        return given()
                .spec(ApiClient.apiRequestWithoutCitizenId())
                .when()
                .get(HOME_ME_PATH);
    }
}
