package br.com.fiap.mvp.services;

import br.com.fiap.mvp.config.ApiClient;
import br.com.fiap.mvp.config.TestConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LocationService {

    private static final String GEOCODE_PATH = ApiClient.API_BASE_PATH + "/location/geocode";

    public Response geocodeConfiguredAddress() {
        return geocodeAddress(TestConfig.address());
    }

    public Response geocodeAddress(String address) {
        return given()
                .spec(ApiClient.apiRequest())
                .queryParam("address", address)
                .when()
                .get(GEOCODE_PATH);
    }

    public Response geocodeWithoutAddress() {
        return given()
                .spec(ApiClient.apiRequest())
                .when()
                .get(GEOCODE_PATH);
    }

    public Response geocodeInvalidAddress() {
        return geocodeAddress("endereco-invalido-mptv-integrated-tests");
    }

    public Response geocodeWithoutCitizenId(String address) {
        return given()
                .spec(ApiClient.apiRequestWithoutCitizenId())
                .queryParam("address", address)
                .when()
                .get(GEOCODE_PATH);
    }
}
