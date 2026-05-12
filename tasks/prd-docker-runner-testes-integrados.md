# PRD - Docker Runner para Testes Integrados MPTV

## 1. Introducao / Visao Geral

Este documento descreve a criacao de suporte Docker para o projeto `mptv-integrated-tests`, com o objetivo de padronizar a execucao dos testes integrados da Meu Ponto Verde API em ambientes locais e de CI.

A funcionalidade deve fornecer um `Dockerfile` com Maven e JDK 21, alem de um `docker-compose.yml` com um servico `tests` capaz de executar a suite Cucumber/Rest Assured contra uma API real ja disponivel.

O Docker nao deve subir backend, banco de dados, seed, massa manual ou mock de geocoding. A API e suas dependencias continuam sendo pre-condicoes externas ao projeto.

## 2. Objetivos

- Permitir que desenvolvedores executem os testes sem depender do Java/Maven instalados na maquina local.
- Padronizar a versao do JDK usada na suite, evitando falhas por Java incompatovel.
- Disponibilizar um comando Docker Compose simples para executar `mvn clean test`.
- Permitir configuracao por `.env`, reaproveitando variaveis como `BASE_URL`, `CITIZEN_ID`, `SLUG` e demais parametros ja existentes.
- Preservar os relatorios Cucumber gerados em `target/cucumber-reports` no host.
- Documentar claramente como executar os testes contra uma API local rodando no host via `host.docker.internal`.

## 3. Historias de Usuario

Como desenvolvedor,
quero executar os testes integrados via Docker,
para nao depender da versao de Java e Maven instalada na minha maquina.

Como desenvolvedor,
quero configurar a URL da API por variavel de ambiente,
para rodar os mesmos testes contra uma API local, remota ou compartilhada.

Como pessoa responsavel pela qualidade,
quero acessar os relatorios Cucumber gerados pelo container,
para analisar os resultados no host apos a execucao.

Como mantenedor do projeto,
quero que o Docker respeite a premissa de testar uma API real ja disponivel,
para evitar que este repositorio assuma responsabilidades de backend, banco ou seed.

## 4. Requisitos Funcionais

1. O projeto deve incluir um `Dockerfile` para executar a suite de testes.
2. O `Dockerfile` deve usar uma imagem base com Maven e JDK 21.
3. O container deve ter diretorio de trabalho configurado para o projeto de testes.
4. O container deve conseguir executar `mvn clean test`.
5. O projeto deve incluir um `docker-compose.yml`.
6. O `docker-compose.yml` deve possuir um servico chamado `tests`.
7. O servico `tests` deve executar `mvn clean test` por padrao.
8. O servico `tests` deve ler variaveis de ambiente a partir de `.env` quando o arquivo existir.
9. O projeto deve manter `.env.example` como referencia das variaveis configuraveis.
10. O Compose deve permitir configurar `BASE_URL`, `CITIZEN_ID`, `SLUG`, `COLLECTION_POINT_ID`, `ADDRESS`, `LAT`, `LNG` e `MAX_DISTANCE_METERS`.
11. O diretorio `target/` deve ser exposto como volume para preservar relatorios e resultados no host.
12. Os relatorios Cucumber devem continuar sendo gerados em `target/cucumber-reports`.
13. A documentacao deve incluir o comando para executar a suite completa via Docker Compose.
14. A documentacao deve explicar que, no Docker Desktop, uma API local no host deve ser acessada com `BASE_URL=http://host.docker.internal:8080`.
15. A documentacao deve explicar que `localhost` dentro do container aponta para o proprio container, nao para a maquina host.
16. A documentacao deve reforcar que a API precisa estar rodando antes da execucao dos testes.
17. A documentacao deve manter os comandos Maven locais existentes.
18. O Docker nao deve alterar os cenarios Cucumber, steps, services ou contratos dos testes.

## 5. Nao Objetivos / Fora de Escopo

- Nao subir a Meu Ponto Verde API neste `docker-compose.yml`.
- Nao subir banco de dados.
- Nao executar seed ou limpeza de dados.
- Nao criar mock de geocoding.
- Nao resolver divergencias atuais de contrato da API.
- Nao substituir a execucao local com Maven; Docker sera uma alternativa padronizada.
- Nao criar pipeline de CI nesta entrega, embora o runner de Docker deva ser util para CI no futuro.

## 6. Consideracoes de Design

- A documentacao deve ser objetiva e voltada para desenvolvedores que precisam executar a suite.
- Os comandos Docker devem seguir o estilo dos comandos Maven ja existentes no `README.md`.
- Os exemplos devem deixar claro quando a execucao aponta para API local no host e quando aponta para outro ambiente.
- As mensagens e instrucoes devem reforcar que falhas de contrato da API podem continuar ocorrendo mesmo com Docker configurado corretamente.

## 7. Consideracoes Tecnicas

- Usar JDK 21 como versao padrao do container.
- O comando padrao do servico `tests` deve ser `mvn clean test`.
- O Compose deve ser adequado para execucao local e para reutilizacao futura em CI.
- O arquivo `.env` nao deve ser versionado.
- O arquivo `.env.example` deve continuar versionado.
- O volume de relatorios deve permitir que arquivos gerados pelo container fiquem disponiveis no host em `target/`.
- Para API local no host com Docker Desktop, o exemplo principal deve usar:

```env
BASE_URL=http://host.docker.internal:8080
```

## 8. Metricas de Sucesso

- Um desenvolvedor consegue executar a suite com Docker Compose sem instalar Java 21 localmente.
- O comando Docker Compose executa `mvn clean test`.
- A suite usa as variaveis configuradas no `.env`.
- Os relatorios Cucumber ficam acessiveis no host apos a execucao.
- A documentacao deixa claro que backend, banco, seed e mocks nao sao iniciados por este projeto.
- A execucao via Docker evita o erro de incompatibilidade entre Java 17 e classes compiladas para Java 21.

## 9. Perguntas em Aberto

- A imagem Docker deve usar JDK 21 estritamente ou permitir JDK 22 como alternativa?
- O projeto deve futuramente publicar uma imagem em registry ou manter apenas build local?
- O CI futuro usara Docker Compose diretamente ou apenas o `Dockerfile` como base?
- Deve existir um comando documentado separado para executar apenas o smoke test via Docker Compose?
