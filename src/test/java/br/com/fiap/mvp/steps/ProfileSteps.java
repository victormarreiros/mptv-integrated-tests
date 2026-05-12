package br.com.fiap.mvp.steps;

import br.com.fiap.mvp.config.ScenarioContext;
import br.com.fiap.mvp.config.TestConfig;
import br.com.fiap.mvp.services.ProfileService;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import static br.com.fiap.mvp.steps.ResponseAssertions.assertEqualsAtPath;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertNonBlankString;

public class ProfileSteps {

    private final ProfileService profileService = new ProfileService();

    @Quando("consulto o perfil do cidadao")
    public void consultoOPerfilDoCidadao() {
        ScenarioContext.current().response(profileService.getProfile());
    }

    @Quando("consulto o perfil sem informar o cidadao")
    public void consultoOPerfilSemInformarOCidadao() {
        ScenarioContext.current().response(profileService.getProfileWithoutCitizenId());
    }

    @Entao("o perfil deve pertencer ao cidadao configurado")
    public void oPerfilDevePertencerAoCidadaoConfigurado() {
        assertEqualsAtPath("id", TestConfig.citizenId());
    }

    @Entao("o perfil deve exibir os dados obrigatorios do cidadao")
    public void oPerfilDeveExibirOsDadosObrigatoriosDoCidadao() {
        assertNonBlankString("fullName");
        assertNonBlankString("joinedAt");
        assertNonBlankString("city");
        assertNonBlankString("state");
        assertNonBlankString("level");
    }
}
