package br.com.fiap.mvp.services;

import br.com.fiap.mvp.config.ApiClient;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HealthService {

    public Response getHealth() {
        return given()
                .spec(ApiClient.request())
                .when()
                .get("/actuator/health");
    }
}
