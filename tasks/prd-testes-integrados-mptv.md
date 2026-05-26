# PRD - Testes integrados da Meu Ponto Verde API

## 1. Introducao / Visao Geral

Este documento descreve a criacao do projeto `mptv-integrated-tests`, um projeto Maven dedicado a automacao de testes integrados da Meu Ponto Verde API.

A funcionalidade resolve a necessidade de substituir a cobertura atual mantida em Postman/Newman por testes automatizados em Java 21, usando Cucumber para descrever os cenarios em linguagem legivel e Rest Assured para executar chamadas HTTP contra a API.

O projeto deve validar os principais fluxos da API ja disponivel, inicialmente em `http://localhost:8080`, cobrindo endpoints de home, perfil, dicas, geolocalizacao, pontos de coleta e residuos. A entrega tambem deve incluir cenarios negativos basicos, preparacao de dados de teste quando necessario, smoke test de saude da API e relatorios gerados pelo Cucumber.

## 2. Objetivos

- Criar um projeto Maven de testes automatizados com Java 21.
- Migrar a cobertura atual dos testes integrados de Postman/Newman para Java, Cucumber e Rest Assured.
- Permitir a execucao dos testes com o comando `mvn test`.
- Validar todos os endpoints iniciais definidos no plano do projeto.
- Garantir que os testes possam ser executados contra a API local em `http://localhost:8080` e contra outros ambientes configuraveis.
- Gerar relatorios Cucumber em HTML, JSON e XML.
- Preparar dados de teste via API quando necessario para que os cenarios sejam reproduziveis.
- Incluir cenarios felizes e cenarios negativos basicos para aumentar a confiabilidade da validacao.

## 3. Historias de Usuario

Como desenvolvedor,
quero executar testes integrados com `mvn test`,
para validar rapidamente se a Meu Ponto Verde API continua funcionando conforme esperado.

Como desenvolvedor,
quero cenarios Cucumber escritos em linguagem clara,
para entender o comportamento esperado da API sem depender de detalhes internos de implementacao.

Como pessoa responsavel pela qualidade,
quero substituir a colecao Postman/Newman por testes Java versionados,
para manter a cobertura integrada em uma base mais facil de evoluir, depurar e integrar ao ciclo de desenvolvimento.

Como desenvolvedor,
quero configurar a URL base, IDs e parametros de teste por propriedades Maven ou variaveis de ambiente,
para executar os mesmos testes em diferentes ambientes.

Como desenvolvedor,
quero que os testes preparem os dados necessarios quando possivel,
para reduzir falhas causadas por ausencia de massa de teste.

Como pessoa responsavel pela entrega,
quero relatorios Cucumber em formatos HTML, JSON e XML,
para analisar os resultados localmente e permitir integracao futura com ferramentas de CI.

## 4. Requisitos Funcionais

1. O projeto deve ser criado como um projeto Maven chamado `mptv-integrated-tests`.
2. O projeto deve usar `groupId` igual a `br.com.fiap.mvp`.
3. O projeto deve usar `artifactId` igual a `mptv-integrated-tests`.
4. O projeto deve ser compativel com Java 21.
5. O projeto deve incluir as dependencias iniciais de Cucumber Java, Cucumber JUnit, Rest Assured, Gson, Lombok e JUnit.
6. O projeto deve organizar os arquivos `.feature` em `src/test/resources/features`.
7. O projeto deve organizar o codigo Java no pacote base `br.com.fiap.mvp`.
8. O projeto deve possuir pacotes ou diretorios para `config`, `runner`, `steps`, `services` e `model`.
9. Os arquivos `.feature` devem descrever o comportamento esperado da API em Gherkin, sem expor detalhes tecnicos desnecessarios de HTTP.
10. Os steps devem traduzir os passos Gherkin para codigo executavel.
11. Os steps nao devem concentrar a logica de chamada HTTP.
12. As chamadas HTTP devem ser centralizadas em classes de service reutilizaveis.
13. O projeto deve possuir services iniciais para Home, Profile, Tips, Location, Collection Points e Residues.
14. Os services devem centralizar montagem de URL, headers, query parameters, path parameters e execucao das requisicoes.
15. O projeto deve possuir modelos ou DTOs para facilitar a serializacao e leitura de respostas JSON quando fizer sentido.
16. O projeto deve permitir configurar `base.url` por propriedade Maven ou variavel de ambiente.
17. O valor padrao de `base.url` deve ser `http://localhost:8080`.
18. O projeto deve permitir configurar `citizen.id` por propriedade Maven ou variavel de ambiente.
19. O valor padrao de `citizen.id` deve ser `661111111111111111111111`.
20. Todos os endpoints sob `/api/v1` devem enviar o header `X-Citizen-Id` com o valor configurado em `citizen.id`.
21. O projeto deve permitir configurar `slug` para consulta de detalhe de dica.
22. O valor padrao de `slug` deve ser `como-descartar-pilhas`.
23. O projeto deve permitir configurar `collection.point.id` para consulta de detalhe de ponto de coleta.
24. O valor padrao de `collection.point.id` deve ser `cp-ecoponto-vila-yara`.
25. O projeto deve permitir configurar `address` para consulta de geocoding.
26. O valor padrao de `address` deve ser `Av Paulista, Sao Paulo`.
27. O projeto deve permitir configurar `lat`, `lng` e `max.distance.meters` para busca de pontos de coleta proximos.
28. O valor padrao de `lat` deve ser `-23.5330`.
29. O valor padrao de `lng` deve ser `-46.7760`.
30. O valor padrao de `max.distance.meters` deve ser `3000`.
31. O projeto deve incluir um smoke test para `/actuator/health`.
32. O smoke test deve validar que a API esta disponivel antes dos cenarios funcionais.
33. O projeto deve cobrir o endpoint `GET /api/v1/home/me`.
34. O teste de home deve validar status `200`.
35. O teste de home deve validar que a resposta e JSON.
36. O teste de home deve validar que o ID do cidadao retornado corresponde ao `citizen.id` configurado.
37. O teste de home deve validar a existencia de `citizen.name`.
38. O teste de home deve validar a existencia de `summary.co2AvoidedKg` e `summary.discardCount`.
39. O teste de home deve validar a existencia de `actions.tipsAvailable` e `actions.highlightLocateButton`.
40. O projeto deve cobrir o endpoint `GET /api/v1/profile/me`.
41. O teste de perfil deve validar status `200`.
42. O teste de perfil deve validar que a resposta e JSON.
43. O teste de perfil deve validar os campos obrigatorios `id`, `fullName`, `joinedAt`, `city`, `state` e `level`.
44. O projeto deve cobrir o endpoint `GET /api/v1/tips`.
45. O teste de listagem de dicas deve validar status `200`.
46. O teste de listagem de dicas deve validar que `items` e uma lista nao vazia.
47. O teste de listagem de dicas deve validar que cada item possui `id`, `title` e `slug`.
48. O projeto deve cobrir o endpoint `GET /api/v1/tips/{slug}`.
49. O teste de detalhe de dica deve validar status `200`.
50. O teste de detalhe de dica deve validar que a resposta contem o `slug` configurado, `title` e `content`.
51. O projeto deve cobrir o endpoint `GET /api/v1/location/geocode`.
52. O teste de geocoding deve consultar a API usando o `address` configurado.
53. O teste de geocoding deve validar status `200`.
54. O teste de geocoding deve validar `formattedAddress`.
55. O teste de geocoding deve validar `location.lat` e `location.lng`.
56. O projeto deve cobrir o endpoint `GET /api/v1/collection-points/{id}`.
57. O teste de detalhe de ponto de coleta deve validar status `200`.
58. O teste de detalhe de ponto de coleta deve validar que o `id` retornado corresponde ao `collection.point.id` configurado.
59. O teste de detalhe de ponto de coleta deve validar `name`, `acceptedResidues` e `location`.
60. O projeto deve cobrir o endpoint `GET /api/v1/collection-points/nearby`.
61. O teste de pontos proximos deve usar `lat`, `lng` e `max.distance.meters` configurados.
62. O teste de pontos proximos deve validar status `200`.
63. O teste de pontos proximos deve validar `searchedLocation`, `points` e `nearestPoint`.
64. O teste de pontos proximos deve validar que `nearestPoint.id` corresponde ao ponto esperado.
65. O projeto deve cobrir o endpoint `GET /api/v1/residues/filter-options`.
66. O teste de opcoes de filtro de residuos deve validar status `200`.
67. O teste de opcoes de filtro de residuos deve validar que `categories` e uma lista nao vazia.
68. O projeto deve incluir cenarios negativos basicos para endpoints relevantes.
69. Os cenarios negativos basicos devem validar pelo menos casos de identificador inexistente, slug inexistente, parametros obrigatorios ausentes ou invalidos, e ausencia do header `X-Citizen-Id` quando aplicavel.
70. Os cenarios negativos devem validar status HTTP esperado e mensagem ou estrutura de erro quando a API retornar esse contrato.
71. O projeto deve preparar dados de teste via API quando um cenario depender de dados que possam ser criados pelo proprio teste.
72. Quando a preparacao de dados via API nao for possivel, o teste deve validar as pre-condicoes e falhar com mensagem clara.
73. A preparacao de dados nao deve depender de alteracao manual no banco de dados.
74. A execucao local padrao deve funcionar com `mvn test`.
75. A execucao deve aceitar sobrescrita da URL base com `mvn test -Dbase.url=http://localhost:8080`.
76. A execucao deve aceitar apontar para outro ambiente com `mvn test -Dbase.url=https://api.exemplo.com`.
77. O runner do Cucumber deve gerar relatorios em `target/cucumber-reports/`.
78. O runner do Cucumber deve gerar relatorio HTML em `target/cucumber-reports/html`.
79. O runner do Cucumber deve gerar relatorio JSON em `target/cucumber-reports/cucumber.json`.
80. O runner do Cucumber deve gerar relatorio JUnit XML em `target/cucumber-reports/cucumber.xml`.

## 5. Nao Objetivos / Fora de Escopo

- O projeto de testes nao deve subir o backend da Meu Ponto Verde API.
- O projeto de testes nao deve subir MongoDB ou qualquer outro banco de dados.
- O projeto de testes nao deve executar seed diretamente no banco de dados.
- O projeto de testes nao deve implementar mock de geocoding.
- O projeto de testes nao deve substituir testes unitarios da API.
- A primeira entrega nao precisa implementar validacoes completas de contrato para todos os campos retornados.
- A primeira entrega nao precisa cobrir testes de carga, performance, seguranca ou resiliencia.
- A primeira entrega nao precisa integrar automaticamente com pipeline de CI, embora os relatorios devam permitir essa integracao no futuro.
- A primeira entrega nao deve implementar funcionalidades da propria API Meu Ponto Verde.

## 6. Consideracoes de Design

- Os cenarios Gherkin devem ser escritos em Portugues do Brasil.
- Os nomes dos cenarios devem descrever o comportamento esperado do ponto de vista de quem consome a API.
- Os arquivos `.feature` devem evitar detalhes como path completo, query string e nomes de classes Java quando isso nao for necessario para entender o comportamento.
- As mensagens de falha dos testes devem ajudar o desenvolvedor a identificar rapidamente qual pre-condicao ou validacao falhou.
- A nomenclatura dos arquivos deve ser simples e consistente, por exemplo `home.feature`, `profile.feature`, `tips.feature`, `location.feature`, `collection-points.feature` e `residues.feature`.

## 7. Consideracoes Tecnicas

- A API base local usada como referencia deve ser `http://localhost:8080`.
- O Swagger UI usado como referencia de contrato deve estar em `http://localhost:8080/swagger-ui/index.html`.
- O OpenAPI usado como referencia tecnica deve estar em `http://localhost:8080/v3/api-docs`.
- Os testes devem chamar diretamente os endpoints REST, sem depender da interface Swagger UI.
- O projeto deve usar Java 21.
- O projeto deve usar Maven como ferramenta de build e execucao.
- O projeto deve usar Cucumber JUnit para executar os cenarios.
- O projeto deve usar Rest Assured para as chamadas HTTP.
- O projeto deve usar Gson para conversao entre JSON e objetos Java quando necessario.
- O projeto pode usar Lombok para reduzir codigo repetitivo em modelos e DTOs.
- A configuracao deve priorizar propriedades Maven, com fallback para variaveis de ambiente e valores padrao locais.
- O estado compartilhado entre steps de um mesmo cenario deve ser controlado de forma clara, por exemplo para armazenar resposta HTTP, payloads e IDs criados durante a preparacao.
- Os cenarios que criarem dados via API devem evitar interferir em outros cenarios.
- Quando houver dados criados pelo teste e existir endpoint de remocao ou limpeza, o projeto deve prever limpeza apos a execucao do cenario.
- Quando nao houver endpoint de limpeza, os dados criados devem usar valores identificaveis como dados de teste.

## 8. Metricas de Sucesso

- O comando `mvn test` deve executar localmente contra `http://localhost:8080`.
- Todos os testes da primeira versao devem passar contra uma API local disponivel e com dados validos.
- Todos os endpoints listados no plano inicial devem possuir pelo menos um cenario feliz automatizado.
- Cada grupo funcional inicial deve possuir pelo menos um cenario negativo basico quando aplicavel.
- O smoke test de `/actuator/health` deve indicar que a API esta pronta antes dos cenarios funcionais.
- Os relatorios HTML, JSON e XML do Cucumber devem ser gerados em `target/cucumber-reports/`.
- A URL base e os principais parametros de teste devem poder ser alterados sem mudanca de codigo.
- Falhas por ausencia de dados ou pre-condicoes devem apresentar mensagens claras para o desenvolvedor.

## 9. Perguntas em Aberto

- Quais endpoints da API existem para criacao e limpeza dos dados necessarios aos testes?
- Qual deve ser o contrato exato de erro para slug inexistente, ID inexistente, parametros invalidos e ausencia do header `X-Citizen-Id`?
- A API retorna sempre o mesmo formato de erro para todos os grupos funcionais?
- Existem limites ou regras especificas para criar dados de teste nos ambientes compartilhados?
- Os cenarios negativos devem validar apenas status HTTP ou tambem campos especificos do corpo de erro?
- O endpoint `/actuator/health` estara disponivel em todos os ambientes em que os testes serao executados?
- O projeto deve futuramente rodar em GitHub Actions, Jenkins ou outra ferramenta de CI?
