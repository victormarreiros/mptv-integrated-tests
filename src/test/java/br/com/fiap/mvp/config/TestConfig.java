package br.com.fiap.mvp.config;

import java.util.Locale;

public final class TestConfig {

    private static final String DEFAULT_BASE_URL = "http://localhost:8080";
    private static final String DEFAULT_CITIZEN_ID = "661111111111111111111111";
    private static final String DEFAULT_SLUG = "como-descartar-pilhas";
    private static final String DEFAULT_COLLECTION_POINT_ID = "cp-ecoponto-vila-yara";
    private static final String DEFAULT_ADDRESS = "Av Paulista, Sao Paulo";
    private static final String DEFAULT_LAT = "-23.5330";
    private static final String DEFAULT_LNG = "-46.7760";
    private static final String DEFAULT_MAX_DISTANCE_METERS = "3000";

    private TestConfig() {
    }

    public static String baseUrl() {
        return get("base.url", DEFAULT_BASE_URL);
    }

    public static String citizenId() {
        return get("citizen.id", DEFAULT_CITIZEN_ID);
    }

    public static String slug() {
        return get("slug", DEFAULT_SLUG);
    }

    public static String collectionPointId() {
        return get("collection.point.id", DEFAULT_COLLECTION_POINT_ID);
    }

    public static String address() {
        return get("address", DEFAULT_ADDRESS);
    }

    public static double lat() {
        return Double.parseDouble(get("lat", DEFAULT_LAT));
    }

    public static double lng() {
        return Double.parseDouble(get("lng", DEFAULT_LNG));
    }

    public static int maxDistanceMeters() {
        return Integer.parseInt(get("max.distance.meters", DEFAULT_MAX_DISTANCE_METERS));
    }

    private static String get(String propertyName, String defaultValue) {
        String propertyValue = normalize(System.getProperty(propertyName));
        if (propertyValue != null) {
            return propertyValue;
        }

        String environmentValue = normalize(System.getenv(toEnvironmentName(propertyName)));
        if (environmentValue != null) {
            return environmentValue;
        }

        return defaultValue;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    private static String toEnvironmentName(String propertyName) {
        return propertyName
                .replace('.', '_')
                .toUpperCase(Locale.ROOT);
    }
}
