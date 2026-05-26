package br.com.fiap.mvp.steps;

import br.com.fiap.mvp.config.ScenarioContext;
import br.com.fiap.mvp.config.TestConfig;
import br.com.fiap.mvp.services.CollectionPointService;
import br.com.fiap.mvp.services.TestDataService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import static br.com.fiap.mvp.steps.ResponseAssertions.assertEqualsAtPath;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertMap;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertNonBlankString;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertNonEmptyList;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertNumber;

public class CollectionPointSteps {

    private final CollectionPointService collectionPointService = new CollectionPointService();
    private final TestDataService testDataService = new TestDataService();

    @Dado("que existe um ponto de coleta configurado para os testes")
    public void queExisteUmPontoDeColetaConfiguradoParaOsTestes() {
        testDataService.validatePrecondition(
                !TestConfig.collectionPointId().isBlank(),
                "A propriedade collection.point.id deve estar configurada para o cenario de ponto de coleta."
        );
    }

    @Dado("que existem coordenadas configuradas para os testes")
    public void queExistemCoordenadasConfiguradasParaOsTestes() {
        testDataService.validatePrecondition(
                TestConfig.maxDistanceMeters() > 0,
                "A propriedade max.distance.meters deve ser maior que zero para busca por proximidade."
        );
    }

    @Quando("consulto o detalhe do ponto de coleta configurado")
    public void consultoODetalheDoPontoDeColetaConfigurado() {
        ScenarioContext.current().response(collectionPointService.getConfiguredCollectionPoint());
    }

    @Quando("busco pontos de coleta proximos")
    public void buscoPontosDeColetaProximos() {
        ScenarioContext.current().response(collectionPointService.getNearbyCollectionPoints());
    }

    @Quando("consulto um ponto de coleta inexistente")
    public void consultoUmPontoDeColetaInexistente() {
        ScenarioContext.current().response(collectionPointService.getNonexistentCollectionPoint());
    }

    @Quando("busco pontos de coleta proximos sem informar coordenadas")
    public void buscoPontosDeColetaProximosSemInformarCoordenadas() {
        ScenarioContext.current().response(collectionPointService.getNearbyCollectionPointsWithoutParameters());
    }

    @Quando("busco pontos de coleta proximos com parametros invalidos")
    public void buscoPontosDeColetaProximosComParametrosInvalidos() {
        ScenarioContext.current().response(collectionPointService.getNearbyCollectionPointsWithInvalidParameters());
    }

    @Entao("o ponto de coleta deve corresponder ao ID configurado")
    public void oPontoDeColetaDeveCorresponderAoIdConfigurado() {
        assertEqualsAtPath("id", TestConfig.collectionPointId());
    }

    @Entao("o ponto de coleta deve exibir nome, residuos aceitos e localizacao")
    public void oPontoDeColetaDeveExibirNomeResiduosAceitosELocalizacao() {
        assertNonBlankString("name");
        assertNonEmptyList("acceptedResidues");
        assertMap("location");
        assertNumber("location.lat");
        assertNumber("location.lng");
    }

    @Entao("a busca deve retornar a localizacao pesquisada")
    public void aBuscaDeveRetornarALocalizacaoPesquisada() {
        assertMap("searchedLocation");
        assertNumber("searchedLocation.lat");
        assertNumber("searchedLocation.lng");
    }

    @Entao("a busca deve retornar pontos e o ponto mais proximo")
    public void aBuscaDeveRetornarPontosEOPontoMaisProximo() {
        assertNonEmptyList("points");
        assertMap("nearestPoint");
        assertEqualsAtPath("nearestPoint.id", TestConfig.collectionPointId());
    }
}
