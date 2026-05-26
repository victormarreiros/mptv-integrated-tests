package br.com.fiap.mvp.config;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ScenarioContext {

    private static final ThreadLocal<ScenarioContext> CURRENT = ThreadLocal.withInitial(ScenarioContext::new);

    private Response response;
    private final Map<String, Object> payloads = new HashMap<>();
    private final Map<String, String> ids = new HashMap<>();
    private final Map<String, Object> attributes = new HashMap<>();

    private ScenarioContext() {
    }

    public static ScenarioContext current() {
        return CURRENT.get();
    }

    public static void reset() {
        CURRENT.remove();
    }

    public Response response() {
        return response;
    }

    public void response(Response response) {
        this.response = response;
    }

    public void payload(String key, Object payload) {
        payloads.put(key, payload);
    }

    public Optional<Object> payload(String key) {
        return Optional.ofNullable(payloads.get(key));
    }

    public void id(String key, String id) {
        ids.put(key, id);
    }

    public Optional<String> id(String key) {
        return Optional.ofNullable(ids.get(key));
    }

    public void attribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Optional<Object> attribute(String key) {
        return Optional.ofNullable(attributes.get(key));
    }
}
