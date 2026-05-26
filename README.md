# mptv-integrated-tests

Projeto Maven de testes integrados da Meu Ponto Verde API, usando Java 21, Cucumber, JUnit e Rest Assured.

Os cenarios validam os fluxos principais da API: saude, home, perfil, dicas, geocoding, pontos de coleta e residuos. A suite chama a API real por HTTP e gera relatorios Cucumber em HTML, JSON e XML.

## Pre-condicoes

- Java 21 ou superior disponivel para o Maven.
- Maven instalado.
- Meu Ponto Verde API rodando e acessivel pela URL configurada.
- Massa de dados compativel com os parametros usados nos testes.

Este projeto nao sobe backend, banco de dados, seed, massa manual ou mock de geocoding. A API e suas dependencias precisam estar prontas antes da execucao.

No ambiente Windows/PowerShell, confirme qual Java o Maven esta usando:

```powershell
mvn -v
```

Se necessario, aponte o `JAVA_HOME` para um JDK compativel antes de executar:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-22'
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
```

## Como Executar

Executar todos os testes com os valores padrao:

```powershell
mvn test
```

Executar informando explicitamente a API local:

```powershell
mvn test "-Dbase.url=http://localhost:8080"
```

Executar contra outro ambiente:

```powershell
mvn test "-Dbase.url=https://api.exemplo.com"
```

Executar apenas o smoke test de saude:

```powershell
mvn test "-Dcucumber.filter.name=API disponivel"
```

No PowerShell, mantenha propriedades Maven com URL entre aspas para evitar parsing incorreto do argumento `-D`.

## Como Executar com Docker

Antes de executar pelo Docker, a Meu Ponto Verde API e suas dependencias devem estar rodando e acessiveis. Este projeto fornece apenas o runner dos testes integrados; ele nao sobe backend, banco, seed, massa manual ou mock de geocoding.

Crie um `.env` local a partir do exemplo:

```powershell
Copy-Item .env.example .env
```

Para API local rodando no host com Docker Desktop, use no `.env`:

```dotenv
BASE_URL=http://host.docker.internal:8080
```

Dentro do container, `localhost` aponta para o proprio container, nao para a maquina host. Por isso, `http://localhost:8080` so deve ser usado quando a API estiver acessivel dentro da mesma rede/container esperado.

Execute a suite pelo servico `tests`:

```powershell
docker compose run --rm tests
```

Os relatorios continuam sendo gerados no host em `target/cucumber-reports/`, porque o Compose monta o diretorio do projeto dentro do container.

## Parametros Configuraveis

A configuracao prioriza propriedades Maven, depois variaveis de ambiente, e por fim valores padrao locais.

| Propriedade Maven | Variavel de ambiente | Padrao |
| --- | --- | --- |
| `base.url` | `BASE_URL` | `http://localhost:8080` |
| `citizen.id` | `CITIZEN_ID` | `661111111111111111111111` |
| `slug` | `SLUG` | `como-descartar-pilhas` |
| `collection.point.id` | `COLLECTION_POINT_ID` | `cp-ecoponto-vila-yara` |
| `address` | `ADDRESS` | `Av Paulista, Sao Paulo` |
| `lat` | `LAT` | `-23.5330` |
| `lng` | `LNG` | `-46.7760` |
| `max.distance.meters` | `MAX_DISTANCE_METERS` | `3000` |

Exemplo com varios parametros:

```powershell
mvn test `
  "-Dbase.url=http://localhost:8080" `
  "-Dcitizen.id=661111111111111111111111" `
  "-Dslug=como-descartar-pilhas" `
  "-Dcollection.point.id=cp-ecoponto-vila-yara"
```

Tambem existe um arquivo `.env.example` com os nomes das variaveis aceitas para referencia local.

## Relatorios

Apos a execucao, os relatorios do Cucumber sao gerados em:

- `target/cucumber-reports/html/index.html`
- `target/cucumber-reports/cucumber.json`
- `target/cucumber-reports/cucumber.xml`

O diretorio `target/` e gerado localmente pelo Maven e nao deve ser versionado.

## Estrutura do Projeto

- `src/test/resources/features` contem os cenarios Gherkin em Portugues do Brasil.
- `src/test/java/br/com/fiap/mvp/steps` conecta os passos Gherkin aos services e validacoes.
- `src/test/java/br/com/fiap/mvp/services` centraliza as chamadas HTTP com Rest Assured.
- `src/test/java/br/com/fiap/mvp/config` centraliza cliente HTTP, parametros e contexto de cenario.
- `src/test/java/br/com/fiap/mvp/runner/CucumberTestRunner.java` configura a execucao Cucumber e os relatorios.

## Pendencias Conhecidas

Alguns contratos da API ainda precisam ser confirmados:

- Home sem `X-Citizen-Id` retornou `500`; o esperado pelos testes e `400`, `401` ou `403`.
- Perfil sem `X-Citizen-Id` retornou `500`; o esperado pelos testes e `400`, `401` ou `403`.
- Geocoding com endereco invalido retornou `200`; o esperado pelos testes e `400`, `404` ou `422`.
- Ainda nao ha contrato definitivo de erro para slug inexistente, ID inexistente, parametros invalidos e ausencia do header `X-Citizen-Id`.
- Ainda nao ha definicao de endpoints de preparacao e limpeza de dados para massa automatizada.

Enquanto esses pontos estiverem abertos, falhas nos cenarios negativos podem indicar divergencia de contrato da API, nao necessariamente erro no projeto de testes.
