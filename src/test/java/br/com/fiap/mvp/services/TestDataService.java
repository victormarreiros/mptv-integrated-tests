package br.com.fiap.mvp.services;

import io.restassured.response.Response;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class TestDataService {

    public static final String AUTOMATED_TEST_MARKER = "mptv-integrated-tests";

    private final List<CleanupAction> cleanupActions = new ArrayList<>();

    public Response prepareViaApi(String description, Supplier<Response> creationCall, int expectedStatusCode) {
        Response response = Objects.requireNonNull(creationCall, "creationCall nao pode ser nulo").get();

        if (response.statusCode() != expectedStatusCode) {
            throw new AssertionError("Falha ao preparar massa de teste para " + description
                    + ". Status esperado: " + expectedStatusCode
                    + ". Status retornado: " + response.statusCode()
                    + ". Corpo: " + response.asString());
        }

        return response;
    }

    public void registerCleanup(String description, Runnable cleanupAction) {
        cleanupActions.add(new CleanupAction(description, cleanupAction));
    }

    public void cleanupCreatedData() {
        AssertionError cleanupFailure = null;

        for (int index = cleanupActions.size() - 1; index >= 0; index--) {
            CleanupAction cleanupAction = cleanupActions.get(index);
            try {
                cleanupAction.run();
            } catch (RuntimeException exception) {
                if (cleanupFailure == null) {
                    cleanupFailure = new AssertionError("Falha ao limpar massa de teste criada pela automacao.");
                }
                cleanupFailure.addSuppressed(new AssertionError(cleanupAction.description(), exception));
            }
        }

        cleanupActions.clear();

        if (cleanupFailure != null) {
            throw cleanupFailure;
        }
    }

    public void validatePrecondition(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Pre-condicao nao atendida: " + message);
        }
    }

    public String automatedTestValue(String prefix) {
        return prefix + "-" + AUTOMATED_TEST_MARKER + "-" + Instant.now().toEpochMilli();
    }

    private record CleanupAction(String description, Runnable action) {

        void run() {
            action.run();
        }
    }
}
