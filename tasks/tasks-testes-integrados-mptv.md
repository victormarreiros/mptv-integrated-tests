# Tasks - Testes integrados da Meu Ponto Verde API

## Arquivos Relevantes

- `pom.xml` - Configuracao Maven do projeto, dependencias, Java 21, identificacao `groupId`/`artifactId` e plugins de teste.
- `README.md` - Documentacao de execucao local, parametros configuraveis e pre-condicoes da API.
- `src/test/resources/features/health.feature` - Cenarios de smoke test para validar que a API esta disponivel.
- `src/test/resources/features/home.feature` - Cenarios da home do cidadao autenticado.
- `src/test/resources/features/profile.feature` - Cenarios do perfil do cidadao autenticado.
- `src/test/resources/features/tips.feature` - Cenarios de listagem e detalhe de dicas.
- `src/test/resources/features/location.feature` - Cenarios de geocoding.
- `src/test/resources/features/collection-points.feature` - Cenarios de detalhe e busca de pontos de coleta proximos.
- `src/test/resources/features/residues.feature` - Cenarios de opcoes de filtro de residuos.
- `src/test/java/br/com/fiap/mvp/runner/CucumberTestRunner.java` - Runner JUnit do Cucumber e configuracao dos relatorios.
- `src/test/java/br/com/fiap/mvp/config/TestConfig.java` - Leitura de propriedades Maven, variaveis de ambiente e valores padrao.
- `src/test/java/br/com/fiap/mvp/config/ApiClient.java` - Configuracao compartilhada do Rest Assured, URL base e headers comuns.
- `src/test/java/br/com/fiap/mvp/config/ScenarioContext.java` - Estado compartilhado por cenario, como resposta HTTP, payloads e IDs criados.
- `src/test/java/br/com/fiap/mvp/services/HealthService.java` - Chamadas para `/actuator/health`.
- `src/test/java/br/com/fiap/mvp/services/HomeService.java` - Chamadas para `/api/v1/home/me`.
- `src/test/java/br/com/fiap/mvp/services/ProfileService.java` - Chamadas para `/api/v1/profile/me`.
- `src/test/java/br/com/fiap/mvp/services/TipsService.java` - Chamadas para `/api/v1/tips` e `/api/v1/tips/{slug}`.
- `src/test/java/br/com/fiap/mvp/services/LocationService.java` - Chamadas para `/api/v1/location/geocode`.
- `src/test/java/br/com/fiap/mvp/services/CollectionPointService.java` - Chamadas para `/api/v1/collection-points/{id}` e `/api/v1/collection-points/nearby`.
- `src/test/java/br/com/fiap/mvp/services/ResidueService.java` - Chamadas para `/api/v1/residues/filter-options`.
- `src/test/java/br/com/fiap/mvp/services/TestDataService.java` - Preparacao e limpeza de dados de teste via API quando os endpoints existirem.
- `src/test/java/br/com/fiap/mvp/steps/*.java` - Steps Cucumber que conectam os cenarios Gherkin aos services e validacoes.
- `src/test/java/br/com/fiap/mvp/model/*.java` - DTOs de requisicao e resposta usados nas validacoes e preparacao de dados.

### Observacoes

- Testes integrados devem ficar em `src/test`, pois serao executados pelo Maven durante `mvn test`.
- As classes de steps devem delegar chamadas HTTP para services; nao concentrar chamadas Rest Assured diretamente nos steps.
- Os cenarios Gherkin devem permanecer em Portugues do Brasil e focados no comportamento esperado da API.
- O projeto de testes nao deve subir backend, banco de dados, seed ou mock de geocoding.
- Use `mvn test` para executar todos os testes.
- Use `mvn test -Dbase.url=http://localhost:8080` para informar explicitamente a API local.
- Use `mvn test -Dbase.url=https://api.exemplo.com` para executar contra outro ambiente.
- Os relatorios esperados devem ser gerados em `target/cucumber-reports/`.

## Instrucoes para Concluir as Tarefas

**IMPORTANTE:** Conforme cada tarefa for concluida, ela deve ser marcada neste arquivo Markdown alterando `- [ ]` para `- [x]`.

Isso ajuda a acompanhar o progresso e garante que nenhuma etapa seja esquecida.

Exemplo:

- `- [ ] 1.1 Ler arquivo` -> `- [x] 1.1 Ler arquivo` apos a conclusao

Atualize o arquivo depois de concluir cada subtarefa, e nao apenas depois de concluir uma tarefa principal inteira.

## Tarefas

- [x] 0.0 Criar branch da feature
  - [x] 0.1 Verificar a branch atual com `git status` e `git branch --show-current`.
  - [x] 0.2 Criar e fazer checkout de uma nova branch para esta funcionalidade. Exemplo: `git checkout -b feature/testes-integrados-mptv`.
  - [x] 0.3 Confirmar que a nova branch esta ativa antes de iniciar as alteracoes.

- [ ] 1.0 Configurar a base do projeto Maven de testes integrados
  - [ ] 1.1 Ajustar o `pom.xml` para usar `groupId` igual a `br.com.fiap.mvp`.
  - [ ] 1.2 Ajustar o `pom.xml` para usar `artifactId` igual a `mptv-integrated-tests`.
  - [ ] 1.3 Confirmar que o projeto compila com Java 21 usando `maven.compiler.source` e `maven.compiler.target`.
  - [ ] 1.4 Garantir que o `pom.xml` inclua Cucumber Java, Cucumber JUnit, Rest Assured, Gson, Lombok e JUnit.
  - [ ] 1.5 Revisar os scopes das dependencias para manter bibliotecas de teste com `test` quando aplicavel.
  - [ ] 1.6 Criar a estrutura `src/test/resources/features`.
  - [ ] 1.7 Criar a estrutura Java base `src/test/java/br/com/fiap/mvp`.
  - [ ] 1.8 Criar os pacotes `config`, `runner`, `steps`, `services` e `model`.

- [ ] 2.0 Implementar configuracao compartilhada de ambiente, cliente HTTP e execucao Cucumber
  - [ ] 2.1 Criar `TestConfig` para ler propriedades Maven via `System.getProperty`.
  - [ ] 2.2 Adicionar fallback para variaveis de ambiente quando a propriedade Maven nao for informada.
  - [ ] 2.3 Definir `base.url` com valor padrao `http://localhost:8080`.
  - [ ] 2.4 Definir `citizen.id` com valor padrao `661111111111111111111111`.
  - [ ] 2.5 Definir `slug` com valor padrao `como-descartar-pilhas`.
  - [ ] 2.6 Definir `collection.point.id` com valor padrao `cp-ecoponto-vila-yara`.
  - [ ] 2.7 Definir `address` com valor padrao `Av Paulista, Sao Paulo`.
  - [ ] 2.8 Definir `lat`, `lng` e `max.distance.meters` com os valores padrao do PRD.
  - [ ] 2.9 Criar `ApiClient` para centralizar configuracao Rest Assured, URL base e Content-Type esperado.
  - [ ] 2.10 Garantir que chamadas sob `/api/v1` enviem o header `X-Citizen-Id`.
  - [ ] 2.11 Permitir chamadas sem `X-Citizen-Id` para cenarios negativos especificos.
  - [ ] 2.12 Criar `ScenarioContext` para armazenar resposta HTTP, payloads e IDs usados durante cada cenario.
  - [ ] 2.13 Criar o runner `CucumberTestRunner` apontando para `src/test/resources/features`.
  - [ ] 2.14 Configurar glue do Cucumber para o pacote `br.com.fiap.mvp.steps`.

- [ ] 3.0 Criar services, modelos e suporte para preparacao de dados de teste
  - [ ] 3.1 Criar `HealthService` para chamar `GET /actuator/health`.
  - [ ] 3.2 Criar `HomeService` para chamar `GET /api/v1/home/me`.
  - [ ] 3.3 Criar `ProfileService` para chamar `GET /api/v1/profile/me`.
  - [ ] 3.4 Criar `TipsService` para chamar `GET /api/v1/tips` e `GET /api/v1/tips/{slug}`.
  - [ ] 3.5 Criar `LocationService` para chamar `GET /api/v1/location/geocode`.
  - [ ] 3.6 Criar `CollectionPointService` para chamar `GET /api/v1/collection-points/{id}` e `GET /api/v1/collection-points/nearby`.
  - [ ] 3.7 Criar `ResidueService` para chamar `GET /api/v1/residues/filter-options`.
  - [ ] 3.8 Criar metodos nos services para chamadas negativas com parametros invalidos, ausentes ou sem header quando aplicavel.
  - [ ] 3.9 Criar DTOs apenas para respostas que ficarem mais claras com modelo Java em vez de validacao direta por JSON path.
  - [ ] 3.10 Criar `TestDataService` para preparar dados via API quando os endpoints de criacao existirem.
  - [ ] 3.11 Criar mecanismo de limpeza de dados quando existir endpoint de remocao ou limpeza.
  - [ ] 3.12 Quando nao houver endpoint para preparar dados, implementar validacao de pre-condicao com mensagem clara.
  - [ ] 3.13 Garantir que dados criados pelos testes usem valores identificaveis como massa automatizada.

- [ ] 4.0 Escrever cenarios Gherkin para smoke test, fluxos felizes e cenarios negativos basicos
  - [ ] 4.1 Criar `health.feature` com cenario para validar que a API esta disponivel.
  - [ ] 4.2 Criar `home.feature` com cenario feliz para consultar a home do cidadao autenticado.
  - [ ] 4.3 Adicionar cenario negativo de home sem `X-Citizen-Id`, se a API exigir esse header.
  - [ ] 4.4 Criar `profile.feature` com cenario feliz para consultar o perfil do cidadao autenticado.
  - [ ] 4.5 Adicionar cenario negativo de perfil sem `X-Citizen-Id`, se a API exigir esse header.
  - [ ] 4.6 Criar `tips.feature` com cenarios felizes de listagem e detalhe por slug.
  - [ ] 4.7 Adicionar cenario negativo para slug de dica inexistente.
  - [ ] 4.8 Criar `location.feature` com cenario feliz de geocoding por endereco.
  - [ ] 4.9 Adicionar cenarios negativos de geocoding com endereco ausente e endereco invalido.
  - [ ] 4.10 Criar `collection-points.feature` com cenarios felizes de detalhe por ID e busca por proximidade.
  - [ ] 4.11 Adicionar cenario negativo para ponto de coleta inexistente.
  - [ ] 4.12 Adicionar cenario negativo para busca por proximidade com parametros invalidos ou ausentes.
  - [ ] 4.13 Criar `residues.feature` com cenario feliz para opcoes de filtro de residuos.
  - [ ] 4.14 Escrever os cenarios em linguagem de comportamento, evitando detalhes tecnicos desnecessarios.

- [ ] 5.0 Implementar steps Cucumber e validacoes dos endpoints da API
  - [ ] 5.1 Criar steps comuns para validar status HTTP esperado.
  - [ ] 5.2 Criar steps comuns para validar que a resposta e JSON quando aplicavel.
  - [ ] 5.3 Implementar steps de health usando `HealthService`.
  - [ ] 5.4 Implementar steps de home usando `HomeService`.
  - [ ] 5.5 Validar em home `citizen.id`, `citizen.name`, `summary.co2AvoidedKg`, `summary.discardCount`, `actions.tipsAvailable` e `actions.highlightLocateButton`.
  - [ ] 5.6 Implementar steps de profile usando `ProfileService`.
  - [ ] 5.7 Validar em profile `id`, `fullName`, `joinedAt`, `city`, `state` e `level`.
  - [ ] 5.8 Implementar steps de tips usando `TipsService`.
  - [ ] 5.9 Validar em tips que `items` e lista nao vazia e que cada item possui `id`, `title` e `slug`.
  - [ ] 5.10 Validar no detalhe de dica o `slug` configurado, `title` e `content`.
  - [ ] 5.11 Implementar steps de location usando `LocationService`.
  - [ ] 5.12 Validar em geocoding `formattedAddress`, `location.lat` e `location.lng`.
  - [ ] 5.13 Implementar steps de collection points usando `CollectionPointService`.
  - [ ] 5.14 Validar no detalhe de ponto de coleta `id`, `name`, `acceptedResidues` e `location`.
  - [ ] 5.15 Validar na busca por proximidade `searchedLocation`, `points` e `nearestPoint`.
  - [ ] 5.16 Validar que `nearestPoint.id` corresponde ao ponto esperado.
  - [ ] 5.17 Implementar steps de residues usando `ResidueService`.
  - [ ] 5.18 Validar em residuos que `categories` e lista nao vazia.
  - [ ] 5.19 Implementar validacoes dos cenarios negativos com status HTTP esperado.
  - [ ] 5.20 Validar corpo ou estrutura de erro nos cenarios negativos quando a API retornar esse contrato.
  - [ ] 5.21 Garantir que mensagens de falha indiquem claramente qual campo, status ou pre-condicao falhou.

- [ ] 6.0 Configurar relatorios Cucumber e comandos de execucao
  - [ ] 6.1 Configurar plugin `pretty` no runner do Cucumber.
  - [ ] 6.2 Configurar relatorio HTML em `target/cucumber-reports/html`.
  - [ ] 6.3 Configurar relatorio JSON em `target/cucumber-reports/cucumber.json`.
  - [ ] 6.4 Configurar relatorio JUnit XML em `target/cucumber-reports/cucumber.xml`.
  - [ ] 6.5 Verificar se `mvn test` executa o runner Cucumber.
  - [ ] 6.6 Verificar se `mvn test -Dbase.url=http://localhost:8080` sobrescreve a URL base.
  - [ ] 6.7 Verificar se os parametros configuraveis podem ser sobrescritos por propriedades Maven.

- [ ] 7.0 Validar a execucao completa e documentar orientacoes de uso
  - [ ] 7.1 Executar `mvn test` com a API local disponivel.
  - [ ] 7.2 Confirmar que todos os endpoints do PRD possuem pelo menos um cenario feliz automatizado.
  - [ ] 7.3 Confirmar que cada grupo funcional possui pelo menos um cenario negativo basico quando aplicavel.
  - [ ] 7.4 Confirmar que o smoke test de `/actuator/health` falha com mensagem clara quando a API nao esta disponivel.
  - [ ] 7.5 Confirmar que os relatorios HTML, JSON e XML sao gerados no diretorio esperado.
  - [ ] 7.6 Atualizar o `README.md` com pre-condicoes, comandos de execucao e parametros configuraveis.
  - [ ] 7.7 Documentar no `README.md` que o projeto nao sobe backend, banco, seed ou mock de geocoding.
  - [ ] 7.8 Registrar perguntas em aberto sobre contratos de erro e endpoints de preparacao/limpeza de dados, caso ainda nao estejam definidos.
