package br.com.fiap.mvp.steps;

import br.com.fiap.mvp.config.ScenarioContext;
import br.com.fiap.mvp.config.TestConfig;
import br.com.fiap.mvp.services.LocationService;
import br.com.fiap.mvp.services.TestDataService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import static br.com.fiap.mvp.steps.ResponseAssertions.assertNonBlankString;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertNumber;

public class LocationSteps {

    private final LocationService locationService = new LocationService();
    private final TestDataService testDataService = new TestDataService();

    @Dado("que existe um endereco configurado para os testes")
    public void queExisteUmEnderecoConfiguradoParaOsTestes() {
        testDataService.validatePrecondition(
                !TestConfig.address().isBlank(),
                "A propriedade address deve estar configurada para o cenario de geocoding."
        );
    }

    @Quando("consulto o geocoding do endereco configurado")
    public void consultoOGeocodingDoEnderecoConfigurado() {
        ScenarioContext.current().response(locationService.geocodeConfiguredAddress());
    }

    @Quando("consulto o geocoding sem informar endereco")
    public void consultoOGeocodingSemInformarEndereco() {
        ScenarioContext.current().response(locationService.geocodeWithoutAddress());
    }

    @Quando("consulto o geocoding com endereco invalido")
    public void consultoOGeocodingComEnderecoInvalido() {
        ScenarioContext.current().response(locationService.geocodeInvalidAddress());
    }

    @Entao("o geocoding deve retornar o endereco formatado")
    public void oGeocodingDeveRetornarOEnderecoFormatado() {
        assertNonBlankString("formattedAddress");
    }

    @Entao("o geocoding deve retornar latitude e longitude")
    public void oGeocodingDeveRetornarLatitudeELongitude() {
        assertNumber("location.lat");
        assertNumber("location.lng");
    }
}
