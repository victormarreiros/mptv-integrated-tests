# AGENTS.md

Guia para agentes e assistentes de IA trabalhando neste repositorio.

## Contexto

Este repositorio contem testes integrados da Meu Ponto Verde API. A suite usa Java 21, Maven, Cucumber JUnit e Rest Assured para chamar a API real por HTTP.

O projeto de testes nao deve subir backend, banco de dados, seed ou mock de geocoding. Antes de executar a suite completa, a API e suas dependencias precisam estar disponiveis.

## Regras de Implementacao

- Mantenha os cenarios Gherkin em Portugues do Brasil e focados em comportamento.
- Coloque novos cenarios em `src/test/resources/features`.
- Steps Cucumber devem orquestrar chamadas e validacoes; nao concentre chamadas Rest Assured nos steps.
- Centralize chamadas HTTP em classes de `src/test/java/br/com/fiap/mvp/services`.
- Centralize parametros configuraveis em `TestConfig`.
- Use `ScenarioContext` para estado compartilhado dentro de um mesmo cenario.
- Preserve mensagens de falha claras, informando campo, status, corpo retornado ou pre-condicao afetada.
- Nao versionar `target/`, `.env`, relatorios gerados ou artefatos locais.

## Execucao

Use Java 21 ou superior no Maven:

```powershell
mvn -v
```

Comandos frequentes:

```powershell
mvn test
mvn test "-Dbase.url=http://localhost:8080"
mvn test "-Dcucumber.filter.name=API disponivel"
```

No PowerShell, coloque argumentos `-D` com URL entre aspas.

## Documentacao e Tasks

- Atualize `README.md` quando mudar comandos, parametros, pre-condicoes ou relatorios.
- Atualize `tasks/tasks-testes-integrados-mptv.md` ao concluir subtarefas.
- Nao marque validacoes completas como concluidas quando a API ainda estiver retornando contratos divergentes.
- Registre contratos indefinidos como perguntas abertas em vez de endurecer validacoes sem confirmacao.

## Pendencias de Contrato

Antes de alterar cenarios negativos, considere as pendencias atuais:

- Ausencia de `X-Citizen-Id` em home/profile pode retornar `500` na API atual, mas os testes esperam erro de requisicao/autorizacao.
- Endereco invalido em geocoding pode retornar `200` na API atual, mas os testes esperam erro ou recurso nao encontrado.
- Endpoints de criacao, seed ou limpeza de dados de teste ainda nao estao definidos.
