package br.com.fiap.mvp.services;

import br.com.fiap.mvp.config.ApiClient;
import br.com.fiap.mvp.config.TestConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TipsService {

    private static final String TIPS_PATH = ApiClient.API_BASE_PATH + "/tips";
    private static final String TIP_DETAIL_PATH = TIPS_PATH + "/{slug}";

    public Response getTips() {
        return given()
                .spec(ApiClient.apiRequest())
                .when()
                .get(TIPS_PATH);
    }

    public Response getTipsWithoutCitizenId() {
        return given()
                .spec(ApiClient.apiRequestWithoutCitizenId())
                .when()
                .get(TIPS_PATH);
    }

    public Response getTipByConfiguredSlug() {
        return getTipBySlug(TestConfig.slug());
    }

    public Response getTipBySlug(String slug) {
        return given()
                .spec(ApiClient.apiRequest())
                .pathParam("slug", slug)
                .when()
                .get(TIP_DETAIL_PATH);
    }

    public Response getTipBySlugWithoutCitizenId(String slug) {
        return given()
                .spec(ApiClient.apiRequestWithoutCitizenId())
                .pathParam("slug", slug)
                .when()
                .get(TIP_DETAIL_PATH);
    }

    public Response getNonexistentTip() {
        return getTipBySlug("slug-inexistente-mptv-integrated-tests");
    }
}
