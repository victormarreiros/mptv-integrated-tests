package br.com.fiap.mvp.services;

import br.com.fiap.mvp.config.ApiClient;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ResidueService {

    private static final String FILTER_OPTIONS_PATH = ApiClient.API_BASE_PATH + "/residues/filter-options";

    public Response getFilterOptions() {
        return given()
                .spec(ApiClient.apiRequest())
                .when()
                .get(FILTER_OPTIONS_PATH);
    }

    public Response getFilterOptionsWithoutCitizenId() {
        return given()
                .spec(ApiClient.apiRequestWithoutCitizenId())
                .when()
                .get(FILTER_OPTIONS_PATH);
    }
}
