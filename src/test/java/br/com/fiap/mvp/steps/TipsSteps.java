package br.com.fiap.mvp.steps;

import br.com.fiap.mvp.config.ScenarioContext;
import br.com.fiap.mvp.config.TestConfig;
import br.com.fiap.mvp.services.TestDataService;
import br.com.fiap.mvp.services.TipsService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.util.List;

import static br.com.fiap.mvp.steps.ResponseAssertions.assertEqualsAtPath;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertNonBlankString;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertNonEmptyList;
import static br.com.fiap.mvp.steps.ResponseAssertions.jsonPath;

public class TipsSteps {

    private final TipsService tipsService = new TipsService();
    private final TestDataService testDataService = new TestDataService();

    @Dado("que existe uma dica configurada para os testes")
    public void queExisteUmaDicaConfiguradaParaOsTestes() {
        testDataService.validatePrecondition(
                !TestConfig.slug().isBlank(),
                "A propriedade slug deve estar configurada para o cenario de detalhe de dica."
        );
    }

    @Quando("consulto as dicas disponiveis")
    public void consultoAsDicasDisponiveis() {
        ScenarioContext.current().response(tipsService.getTips());
    }

    @Quando("consulto o detalhe da dica configurada")
    public void consultoODetalheDaDicaConfigurada() {
        ScenarioContext.current().response(tipsService.getTipByConfiguredSlug());
    }

    @Quando("consulto uma dica inexistente")
    public void consultoUmaDicaInexistente() {
        ScenarioContext.current().response(tipsService.getNonexistentTip());
    }

    @Entao("a lista de dicas deve conter itens com identificacao, titulo e slug")
    public void aListaDeDicasDeveConterItensComIdentificacaoTituloESlug() {
        assertNonEmptyList("items");
        List<?> items = jsonPath().getList("items");

        for (int index = 0; index < items.size(); index++) {
            assertNonBlankString("items[" + index + "].id");
            assertNonBlankString("items[" + index + "].title");
            assertNonBlankString("items[" + index + "].slug");
        }
    }

    @Entao("o detalhe da dica deve corresponder ao slug configurado")
    public void oDetalheDaDicaDeveCorresponderAoSlugConfigurado() {
        assertEqualsAtPath("slug", TestConfig.slug());
    }

    @Entao("o detalhe da dica deve conter titulo e conteudo")
    public void oDetalheDaDicaDeveConterTituloEConteudo() {
        assertNonBlankString("title");
        assertNonBlankString("content");
    }
}
