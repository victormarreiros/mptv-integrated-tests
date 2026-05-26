package br.com.fiap.mvp.steps;

import br.com.fiap.mvp.config.ScenarioContext;
import br.com.fiap.mvp.config.TestConfig;
import br.com.fiap.mvp.services.HomeService;
import br.com.fiap.mvp.services.TestDataService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import static br.com.fiap.mvp.steps.ResponseAssertions.assertBoolean;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertEqualsAtPath;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertNonBlankString;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertNumber;

public class HomeSteps {

    private final HomeService homeService = new HomeService();
    private final TestDataService testDataService = new TestDataService();

    @Dado("que existe um cidadao configurado para os testes")
    public void queExisteUmCidadaoConfiguradoParaOsTestes() {
        testDataService.validatePrecondition(
                !TestConfig.citizenId().isBlank(),
                "A propriedade citizen.id deve estar configurada para os cenarios autenticados."
        );
    }

    @Quando("consulto a home do cidadao")
    public void consultoAHomeDoCidadao() {
        ScenarioContext.current().response(homeService.getHome());
    }

    @Quando("consulto a home sem informar o cidadao")
    public void consultoAHomeSemInformarOCidadao() {
        ScenarioContext.current().response(homeService.getHomeWithoutCitizenId());
    }

    @Entao("a home deve pertencer ao cidadao configurado")
    public void aHomeDevePertencerAoCidadaoConfigurado() {
        assertEqualsAtPath("citizen.id", TestConfig.citizenId());
    }

    @Entao("a home deve exibir o resumo e as acoes principais do cidadao")
    public void aHomeDeveExibirOResumoEAsAcoesPrincipaisDoCidadao() {
        assertNonBlankString("citizen.name");
        assertNumber("summary.co2AvoidedKg");
        assertNumber("summary.discardCount");
        assertBoolean("actions.tipsAvailable");
        assertBoolean("actions.highlightLocateButton");
    }
}
