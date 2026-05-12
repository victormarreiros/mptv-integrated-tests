package br.com.fiap.mvp.steps;

import br.com.fiap.mvp.config.ScenarioContext;
import br.com.fiap.mvp.services.ResidueService;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import static br.com.fiap.mvp.steps.ResponseAssertions.assertNonEmptyList;

public class ResidueSteps {

    private final ResidueService residueService = new ResidueService();

    @Quando("consulto as opcoes de filtro de residuos")
    public void consultoAsOpcoesDeFiltroDeResiduos() {
        ScenarioContext.current().response(residueService.getFilterOptions());
    }

    @Entao("as opcoes de filtro devem conter categorias de residuos")
    public void asOpcoesDeFiltroDevemConterCategoriasDeResiduos() {
        assertNonEmptyList("categories");
    }
}
