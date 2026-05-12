package br.com.fiap.mvp.steps;

import br.com.fiap.mvp.config.ScenarioContext;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

final class ResponseAssertions {

    private ResponseAssertions() {
    }

    static Response response() {
        Response response = ScenarioContext.current().response();
        Assert.assertNotNull("Nenhuma resposta HTTP foi armazenada no contexto do cenario.", response);
        return response;
    }

    static JsonPath jsonPath() {
        return response().jsonPath();
    }

    static void assertStatus(int expectedStatus) {
        Response response = response();
        Assert.assertEquals(
                "Status HTTP inesperado. Corpo retornado: " + response.asString(),
                expectedStatus,
                response.statusCode()
        );
    }

    static void assertStatusIn(String description, int... expectedStatuses) {
        Response response = response();
        int actualStatus = response.statusCode();

        for (int expectedStatus : expectedStatuses) {
            if (actualStatus == expectedStatus) {
                return;
            }
        }

        Assert.fail(description + ". Status esperado: " + join(expectedStatuses)
                + ". Status retornado: " + actualStatus
                + ". Corpo: " + response.asString());
    }

    static void assertJsonResponse() {
        String contentType = response().contentType();
        Assert.assertTrue(
                "A resposta deveria ser JSON, mas retornou Content-Type: " + contentType,
                contentType != null && contentType.toLowerCase(Locale.ROOT).contains("json")
        );
    }

    static void assertMatchesSchema(String schemaPath) {
        response()
                .then()
                .body(matchesJsonSchemaInClasspath(schemaPath));
    }

    static void assertEqualsAtPath(String path, Object expected) {
        Object actual = jsonPath().get(path);
        Assert.assertEquals("Valor inesperado no campo JSON '" + path + "'.", expected, actual);
    }

    static void assertRequired(String path) {
        Object value = jsonPath().get(path);
        Assert.assertNotNull("Campo obrigatorio ausente no JSON: " + path, value);
    }

    static void assertNonBlankString(String path) {
        String value = jsonPath().getString(path);
        Assert.assertTrue(
                "Campo obrigatorio deveria ser texto nao vazio: " + path,
                value != null && !value.isBlank()
        );
    }

    static void assertNumber(String path) {
        Object value = jsonPath().get(path);
        Assert.assertTrue(
                "Campo obrigatorio deveria ser numerico: " + path + ". Valor retornado: " + value,
                value instanceof Number
        );
    }

    static void assertBoolean(String path) {
        Object value = jsonPath().get(path);
        Assert.assertTrue(
                "Campo obrigatorio deveria ser booleano: " + path + ". Valor retornado: " + value,
                value instanceof Boolean
        );
    }

    static void assertMap(String path) {
        Object value = jsonPath().get(path);
        Assert.assertTrue(
                "Campo obrigatorio deveria ser um objeto JSON: " + path + ". Valor retornado: " + value,
                value instanceof Map<?, ?>
        );
    }

    static void assertNonEmptyList(String path) {
        Object value = jsonPath().get(path);
        Assert.assertTrue(
                "Campo obrigatorio deveria ser lista nao vazia: " + path + ". Valor retornado: " + value,
                value instanceof Collection<?> collection && !collection.isEmpty()
        );
    }

    static void assertOptionalErrorContract(String expectedContract) {
        Response response = response();
        String body = response.asString();

        if (body == null || body.isBlank() || !isJson(response, body)) {
            return;
        }

        Object root = response.jsonPath().get();
        if (root instanceof Map<?, ?> map) {
            Assert.assertTrue(
                    "A resposta de erro deveria possuir estrutura reconhecivel para " + expectedContract
                            + ". Corpo: " + body,
                    hasRecognizableErrorField(map)
            );
            return;
        }

        if (root instanceof Collection<?> collection) {
            Assert.assertFalse(
                    "A resposta de erro para " + expectedContract + " retornou uma lista vazia.",
                    collection.isEmpty()
            );
        }
    }

    private static boolean hasRecognizableErrorField(Map<?, ?> map) {
        return map.keySet().stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .map(key -> key.toLowerCase(Locale.ROOT))
                .anyMatch(key -> key.equals("status")
                        || key.equals("error")
                        || key.equals("message")
                        || key.equals("title")
                        || key.equals("detail")
                        || key.equals("path")
                        || key.equals("timestamp")
                        || key.equals("errors")
                        || key.equals("violations"));
    }

    private static boolean isJson(Response response, String body) {
        String contentType = response.contentType();
        return contentType != null && contentType.toLowerCase(Locale.ROOT).contains("json")
                || body.trim().startsWith("{")
                || body.trim().startsWith("[");
    }

    private static String join(int... values) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < values.length; index++) {
            if (index > 0) {
                builder.append(", ");
            }
            builder.append(values[index]);
        }
        return builder.toString();
    }
}
