# Plano inicial - mptv-integrated-tests

## Objetivo

Criar um projeto dedicado de automacao de testes integrados para a Meu Ponto Verde API.
O projeto tera repositorio proprio e sera executado contra uma API ja disponivel, usando
como referencia local:

- API base: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI: `http://localhost:8080/v3/api-docs`

O objetivo inicial e migrar a cobertura hoje mantida em
`scripts/integrated-tests/`, substituindo a colecao Postman/Newman por testes
Java com Cucumber e Rest Assured.

## Identificacao do projeto

- Nome do projeto: `mptv-integrated-tests`
- `groupId`: `br.com.fiap.mvp`
- `artifactId`: `mptv-integrated-tests`
- Tipo: projeto Maven de testes automatizados
- Runtime alvo: Java 21

## Stack tecnica

O projeto deve usar:

- Java 21
- Maven
- Lombok
- Cucumber Java
- Cucumber JUnit
- Rest Assured
- Google Gson

Dependencias iniciais previstas:

- `io.cucumber:cucumber-java`
- `io.cucumber:cucumber-junit`
- `io.rest-assured:rest-assured`
- `com.google.code.gson:gson`
- `org.projectlombok:lombok`
- `junit:junit`

## Estrutura esperada

Estrutura base do projeto:

```text
src/test/
  resources/
    features/
  java/
    steps/
    services/
    model/
```

Estrutura recomendada com pacote Java:

```text
src/test/
  resources/
    features/
      home.feature
      profile.feature
      tips.feature
      location.feature
      collection-points.feature
      residues.feature
  java/
    br/com/fiap/mvp/
      config/
      runner/
      steps/
      services/
      model/
```

## Responsabilidade das camadas

### `src/test/resources/features`

Contem os cenarios de teste em Gherkin, escritos de forma descritiva e legivel.
Os arquivos `.feature` devem representar comportamento esperado da API, sem
detalhes de implementacao HTTP.

Exemplo de intencao:

```gherkin
Funcionalidade: Home do cidadao

  Cenario: Consultar resumo da home do cidadao autenticado
    Dado que existe um cidadao valido para os testes integrados
    Quando consulto a home do cidadao
    Entao a API deve retornar status 200
    E a resposta deve conter os dados do cidadao
    E a resposta deve conter o resumo da carteira verde
```

### `src/test/java/steps`

Contem os metodos Java que traduzem as etapas escritas em Gherkin para codigo
executavel. Essa camada deve coordenar o fluxo dos cenarios e delegar chamadas
HTTP para `services`.

Regras:

- nao concentrar logica de chamada HTTP diretamente nos steps;
- manter os steps focados em Dado/Quando/Entao;
- reutilizar estado do cenario para resposta HTTP, payloads e IDs.

### `src/test/java/services`

Contem classes reutilizaveis para operacoes da API usando Rest Assured.
Cada service representa um grupo de endpoints ou uma capacidade da API.

Servicos iniciais:

- `HomeService`
- `ProfileService`
- `TipsService`
- `LocationService`
- `CollectionPointService`
- `ResidueService`

Essas classes devem centralizar:

- montagem de URL;
- headers padrao;
- query parameters;
- path parameters;
- execucao das requisicoes;
- retorno de `Response` ou DTOs quando fizer sentido.

### `src/test/java/model`

Contem modelos e DTOs usados para representar dados de requisicoes e respostas.
Esses objetos facilitam a conversao entre JSON e Java com Gson e tornam as
validacoes mais claras.

Modelos iniciais esperados:

- respostas de home;
- respostas de perfil;
- lista e detalhe de dicas;
- resposta de geocoding;
- detalhe e busca de pontos de coleta;
- opcoes de filtro de residuos.

## Configuracao de execucao

O projeto deve aceitar configuracao por propriedades do Maven ou variaveis de
ambiente, com valores padrao para execucao local.

Propriedades iniciais:

| Propriedade | Valor padrao | Uso |
| --- | --- | --- |
| `base.url` | `http://localhost:8080` | URL base da API |
| `citizen.id` | `661111111111111111111111` | Valor do header `X-Citizen-Id` |
| `slug` | `como-descartar-pilhas` | Slug usado no detalhe de dica |
| `collection.point.id` | `cp-ecoponto-vila-yara` | ID de ponto de coleta conhecido |
| `address` | `Av Paulista, Sao Paulo` | Endereco usado no geocode |
| `lat` | `-23.5330` | Latitude usada na busca por proximidade |
| `lng` | `-46.7760` | Longitude usada na busca por proximidade |
| `max.distance.meters` | `3000` | Raio usado na busca por proximidade |

Todos os endpoints sob `/api/v1` devem enviar o header:

```http
X-Citizen-Id: 661111111111111111111111
```

## Endpoints cobertos inicialmente

A primeira versao deve reproduzir a cobertura atual dos testes integrados:

| Grupo | Metodo | Endpoint | Validacoes principais |
| --- | --- | --- | --- |
| Home | `GET` | `/api/v1/home/me` | status 200, JSON, cidadao, resumo e acoes |
| Profile | `GET` | `/api/v1/profile/me` | status 200, JSON e campos obrigatorios do perfil |
| Tips | `GET` | `/api/v1/tips` | status 200, JSON e lista nao vazia |
| Tips | `GET` | `/api/v1/tips/{slug}` | status 200, JSON e slug esperado |
| Location | `GET` | `/api/v1/location/geocode` | status 200, endereco formatado e coordenadas |
| Collection Points | `GET` | `/api/v1/collection-points/{id}` | status 200, ID esperado, nome, residuos e localizacao |
| Collection Points | `GET` | `/api/v1/collection-points/nearby` | status 200, lista de pontos e ponto mais proximo esperado |
| Residues | `GET` | `/api/v1/residues/filter-options` | status 200 e categorias disponiveis |

Um smoke test para `/actuator/health` pode ser adicionado para validar que a API
esta pronta antes dos cenarios funcionais.

## Cenarios iniciais

### Home

- Consultar `/api/v1/home/me`.
- Validar status `200`.
- Validar resposta JSON.
- Validar que `citizen.id` corresponde ao `citizen.id` configurado.
- Validar existencia de `citizen.name`.
- Validar existencia de `summary.co2AvoidedKg` e `summary.discardCount`.
- Validar existencia de `actions.tipsAvailable` e `actions.highlightLocateButton`.

### Profile

- Consultar `/api/v1/profile/me`.
- Validar status `200`.
- Validar resposta JSON.
- Validar `id`, `fullName`, `joinedAt`, `city`, `state` e `level`.

### Tips

- Consultar `/api/v1/tips`.
- Validar status `200`.
- Validar que `items` e uma lista nao vazia.
- Validar que cada item possui `id`, `title` e `slug`.
- Consultar `/api/v1/tips/{slug}`.
- Validar que o detalhe retorna o `slug`, `title` e `content`.

### Location

- Consultar `/api/v1/location/geocode?address={address}`.
- Validar status `200`.
- Validar `formattedAddress`.
- Validar `location.lat` e `location.lng`.

### Collection Points

- Consultar `/api/v1/collection-points/{id}`.
- Validar status `200`.
- Validar que o `id` retornado corresponde ao ID configurado.
- Validar `name`, `acceptedResidues` e `location`.
- Consultar `/api/v1/collection-points/nearby`.
- Validar `searchedLocation`, `points` e `nearestPoint`.
- Validar que `nearestPoint.id` corresponde ao ponto esperado.

### Residues

- Consultar `/api/v1/residues/filter-options`.
- Validar status `200`.
- Validar que `categories` e uma lista nao vazia.

## Comandos de execucao

Execucao local padrao:

```bash
mvn test
```

Execucao apontando explicitamente para a API local:

```bash
mvn test -Dbase.url=http://localhost:8080
```

Execucao apontando para outro ambiente:

```bash
mvn test -Dbase.url=https://api.exemplo.com
```

## Relatorios

O runner do Cucumber deve gerar relatorios em `target/cucumber-reports/`.

Plugins recomendados:

- `pretty`
- `html:target/cucumber-reports/html`
- `json:target/cucumber-reports/cucumber.json`
- `junit:target/cucumber-reports/cucumber.xml`

## Premissas

- O projeto `mptv-integrated-tests` sera mantido em repositorio proprio.
- O projeto de testes nao sera responsavel por subir backend, MongoDB, seed ou
  mock de geocoding.
- A API deve estar disponivel antes da execucao dos testes.
- O ambiente testado deve conter dados equivalentes aos usados hoje pela seed de
  integracao.
- O Swagger em `http://localhost:8080/swagger-ui/index.html` sera usado como
  referencia de contrato, mas os testes chamarao diretamente os endpoints REST.
- A primeira versao cobre os cenarios felizes ja existentes; cenarios negativos,
  contratos mais rigorosos e validacoes de erro podem ser planejados depois.

