package br.com.fiap.mvp.steps;

import br.com.fiap.mvp.config.TestConfig;
import br.com.fiap.mvp.config.ScenarioContext;
import br.com.fiap.mvp.services.HealthService;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;

import static br.com.fiap.mvp.steps.ResponseAssertions.assertEqualsAtPath;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertMatchesSchema;

public class HealthSteps {

    private final HealthService healthService = new HealthService();

    @Quando("consulto a saude da API")
    public void consultoASaudeDaApi() {
        try {
            ScenarioContext.current().response(healthService.getHealth());
        } catch (Exception exception) {
            Assert.fail("Pre-condicao falhou: a API nao esta disponivel em " + TestConfig.baseUrl()
                    + " ao consultar /actuator/health. Verifique se o backend e suas dependencias estao em execucao. "
                    + "Erro original: " + exception.getClass().getSimpleName() + " - " + exception.getMessage());
        }
    }

    @Entao("a API deve indicar que esta disponivel")
    public void aApiDeveIndicarQueEstaDisponivel() {
        assertEqualsAtPath("status", "UP");
    }

    @Entao("a resposta de saude deve respeitar o schema esperado")
    public void aRespostaDeSaudeDeveRespeitarOSchemaEsperado() {
        assertMatchesSchema("schemas/health.schema.json");
    }
}
