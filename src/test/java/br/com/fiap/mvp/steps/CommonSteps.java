package br.com.fiap.mvp.steps;

import br.com.fiap.mvp.config.ScenarioContext;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Entao;

import static br.com.fiap.mvp.steps.ResponseAssertions.assertJsonResponse;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertOptionalErrorContract;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertStatus;
import static br.com.fiap.mvp.steps.ResponseAssertions.assertStatusIn;

public class CommonSteps {

    @Before
    public void resetScenarioContext() {
        ScenarioContext.reset();
    }

    @Entao("a resposta deve ter status {int}")
    public void aRespostaDeveTerStatus(int expectedStatus) {
        assertStatus(expectedStatus);
    }

    @Entao("a resposta deve ser JSON")
    public void aRespostaDeveSerJson() {
        assertJsonResponse();
    }

    @Entao("a resposta deve ter status de requisicao nao autorizada ou invalida")
    public void aRespostaDeveTerStatusDeRequisicaoNaoAutorizadaOuInvalida() {
        assertStatusIn("A requisicao sem identificacao do cidadao deveria ser recusada", 400, 401, 403);
    }

    @Entao("a resposta deve ter status de endereco nao encontrado ou requisicao invalida")
    public void aRespostaDeveTerStatusDeEnderecoNaoEncontradoOuRequisicaoInvalida() {
        assertStatusIn("O endereco invalido deveria ser recusado ou nao encontrado", 400, 404, 422);
    }

    @Entao("a resposta deve informar que a identificacao do cidadao e obrigatoria quando esse contrato estiver disponivel")
    public void aRespostaDeveInformarQueAIdentificacaoDoCidadaoEObrigatoriaQuandoEsseContratoEstiverDisponivel() {
        assertOptionalErrorContract("identificacao obrigatoria do cidadao");
    }

    @Entao("a resposta deve informar que o recurso nao foi encontrado quando esse contrato estiver disponivel")
    public void aRespostaDeveInformarQueORecursoNaoFoiEncontradoQuandoEsseContratoEstiverDisponivel() {
        assertOptionalErrorContract("recurso nao encontrado");
    }

    @Entao("a resposta deve informar que o endereco e obrigatorio quando esse contrato estiver disponivel")
    public void aRespostaDeveInformarQueOEnderecoEObrigatorioQuandoEsseContratoEstiverDisponivel() {
        assertOptionalErrorContract("endereco obrigatorio");
    }

    @Entao("a resposta deve informar que o endereco nao pode ser geocodificado quando esse contrato estiver disponivel")
    public void aRespostaDeveInformarQueOEnderecoNaoPodeSerGeocodificadoQuandoEsseContratoEstiverDisponivel() {
        assertOptionalErrorContract("endereco nao geocodificado");
    }

    @Entao("a resposta deve informar que os parametros de busca sao obrigatorios quando esse contrato estiver disponivel")
    public void aRespostaDeveInformarQueOsParametrosDeBuscaSaoObrigatoriosQuandoEsseContratoEstiverDisponivel() {
        assertOptionalErrorContract("parametros obrigatorios da busca");
    }

    @Entao("a resposta deve informar que os parametros de busca sao invalidos quando esse contrato estiver disponivel")
    public void aRespostaDeveInformarQueOsParametrosDeBuscaSaoInvalidosQuandoEsseContratoEstiverDisponivel() {
        assertOptionalErrorContract("parametros invalidos da busca");
    }
}
