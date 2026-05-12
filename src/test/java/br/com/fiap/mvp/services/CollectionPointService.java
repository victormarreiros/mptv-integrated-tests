package br.com.fiap.mvp.services;

import br.com.fiap.mvp.config.ApiClient;
import br.com.fiap.mvp.config.TestConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CollectionPointService {

    private static final String COLLECTION_POINT_DETAIL_PATH = ApiClient.API_BASE_PATH + "/collection-points/{id}";
    private static final String COLLECTION_POINTS_NEARBY_PATH = ApiClient.API_BASE_PATH + "/collection-points/nearby";

    public Response getConfiguredCollectionPoint() {
        return getCollectionPointById(TestConfig.collectionPointId());
    }

    public Response getCollectionPointById(String id) {
        return given()
                .spec(ApiClient.apiRequest())
                .pathParam("id", id)
                .when()
                .get(COLLECTION_POINT_DETAIL_PATH);
    }

    public Response getCollectionPointByIdWithoutCitizenId(String id) {
        return given()
                .spec(ApiClient.apiRequestWithoutCitizenId())
                .pathParam("id", id)
                .when()
                .get(COLLECTION_POINT_DETAIL_PATH);
    }

    public Response getNonexistentCollectionPoint() {
        return getCollectionPointById("cp-inexistente-mptv-integrated-tests");
    }

    public Response getNearbyCollectionPoints() {
        return getNearbyCollectionPoints(TestConfig.lat(), TestConfig.lng(), TestConfig.maxDistanceMeters());
    }

    public Response getNearbyCollectionPoints(double lat, double lng, int maxDistanceMeters) {
        return given()
                .spec(ApiClient.apiRequest())
                .queryParam("lat", lat)
                .queryParam("lng", lng)
                .queryParam("maxDistanceMeters", maxDistanceMeters)
                .when()
                .get(COLLECTION_POINTS_NEARBY_PATH);
    }

    public Response getNearbyCollectionPointsWithoutCitizenId(double lat, double lng, int maxDistanceMeters) {
        return given()
                .spec(ApiClient.apiRequestWithoutCitizenId())
                .queryParam("lat", lat)
                .queryParam("lng", lng)
                .queryParam("maxDistanceMeters", maxDistanceMeters)
                .when()
                .get(COLLECTION_POINTS_NEARBY_PATH);
    }

    public Response getNearbyCollectionPointsWithoutParameters() {
        return given()
                .spec(ApiClient.apiRequest())
                .when()
                .get(COLLECTION_POINTS_NEARBY_PATH);
    }

    public Response getNearbyCollectionPointsWithInvalidParameters() {
        return given()
                .spec(ApiClient.apiRequest())
                .queryParam("lat", "latitude-invalida")
                .queryParam("lng", "longitude-invalida")
                .queryParam("maxDistanceMeters", -1)
                .when()
                .get(COLLECTION_POINTS_NEARBY_PATH);
    }
}
