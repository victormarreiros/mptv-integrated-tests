# Tasks - Docker Runner para Testes Integrados MPTV

## Arquivos Relevantes

- `Dockerfile` - Define a imagem com Maven e JDK 21 para executar a suite de testes integrados.
- `docker-compose.yml` - Configura o servico `tests`, variaveis de ambiente e volume para preservar relatorios.
- `.env.example` - Exemplo de configuracao local para `BASE_URL`, `CITIZEN_ID`, `SLUG` e demais parametros aceitos.
- `.gitignore` - Garante que `.env`, arquivos locais e artefatos gerados continuem fora do versionamento.
- `README.md` - Documenta comandos Docker, pre-condicoes, uso de `host.docker.internal` e caminhos dos relatorios.
- `pom.xml` - Projeto Maven executado dentro do container; relevante para validar compatibilidade com JDK 21.
- `tasks/prd-docker-runner-testes-integrados.md` - PRD de referencia para escopo e criterios da feature.

### Observacoes

- Esta feature deve criar apenas o runner Docker dos testes; nao deve subir backend, banco, seed ou mock de geocoding.
- A API da Meu Ponto Verde deve estar rodando e acessivel antes da execucao do container.
- Para API local no host usando Docker Desktop, use `BASE_URL=http://host.docker.internal:8080`.
- O comando principal esperado e `docker compose run --rm tests`.
- Os relatorios devem continuar disponiveis em `target/cucumber-reports/` no host.

## Instrucoes para Concluir as Tarefas

**IMPORTANTE:** Conforme cada tarefa for concluida, ela deve ser marcada neste arquivo Markdown alterando `- [ ]` para `- [x]`.

Isso ajuda a acompanhar o progresso e garante que nenhuma etapa seja esquecida.

Exemplo:

- `- [ ] 1.1 Ler arquivo` -> `- [x] 1.1 Ler arquivo` apos a conclusao

Atualize o arquivo depois de concluir cada subtarefa, e nao apenas depois de concluir uma tarefa principal inteira.

## Tarefas

- [x] 1.0 Criar imagem Docker para execucao dos testes integrados
  - [x] 1.1 Criar `Dockerfile` na raiz do projeto.
  - [x] 1.2 Usar uma imagem base com Maven e JDK 21.
  - [x] 1.3 Definir o diretorio de trabalho do container para o projeto de testes.
  - [x] 1.4 Copiar `pom.xml` antes do codigo-fonte para favorecer cache de dependencias quando aplicavel.
  - [x] 1.5 Copiar `src/` e demais arquivos necessarios para a execucao dos testes.
  - [x] 1.6 Definir `mvn clean test` como comando padrao da imagem.
  - [x] 1.7 Garantir que a imagem nao inclua configuracao para subir backend, banco, seed ou mock.

- [x] 2.0 Configurar Docker Compose com servico `tests`
  - [x] 2.1 Criar `docker-compose.yml` na raiz do projeto.
  - [x] 2.2 Definir um servico chamado `tests`.
  - [x] 2.3 Configurar o servico `tests` para construir a imagem a partir do `Dockerfile` local.
  - [x] 2.4 Configurar o comando padrao do servico como `mvn clean test`.
  - [x] 2.5 Configurar leitura de variaveis por `env_file` apontando para `.env`.
  - [x] 2.6 Mapear `BASE_URL`, `CITIZEN_ID`, `SLUG`, `COLLECTION_POINT_ID`, `ADDRESS`, `LAT`, `LNG` e `MAX_DISTANCE_METERS` como variaveis aceitas pelo container.
  - [x] 2.7 Configurar volume para preservar `target/` no host apos a execucao.
  - [x] 2.8 Confirmar que o Compose nao define servicos de API, banco, seed ou mock.

- [x] 3.0 Ajustar configuracao de ambiente para uso via Docker
  - [x] 3.1 Revisar `.env.example` para garantir que contem todas as variaveis consumidas pelos testes.
  - [x] 3.2 Ajustar o valor de exemplo de `BASE_URL` para orientar uso com API local no host via `http://host.docker.internal:8080`, se isso nao prejudicar a execucao Maven local documentada.
  - [x] 3.3 Manter claro no `.env.example` que propriedades Maven continuam podendo sobrescrever configuracoes quando os testes forem executados fora do Docker.
  - [x] 3.4 Confirmar que `.gitignore` ignora `.env` e variacoes locais sem ignorar `.env.example`.
  - [x] 3.5 Evitar qualquer alteracao em `TestConfig` se as variaveis atuais ja forem suficientes para o Docker.

- [x] 4.0 Atualizar documentacao de execucao com Docker
  - [x] 4.1 Adicionar ao `README.md` uma secao de execucao via Docker.
  - [x] 4.2 Documentar o comando principal `docker compose run --rm tests`.
  - [x] 4.3 Documentar como criar um `.env` local a partir de `.env.example`.
  - [x] 4.4 Explicar que, dentro do container, `localhost` aponta para o proprio container.
  - [x] 4.5 Documentar `BASE_URL=http://host.docker.internal:8080` para API local no host com Docker Desktop.
  - [x] 4.6 Reforcar que este projeto nao sobe backend, banco, seed ou mock de geocoding.
  - [x] 4.7 Documentar que os relatorios continuam sendo gerados em `target/cucumber-reports/`.
  - [x] 4.8 Manter os comandos Maven locais existentes no README.

- [ ] 5.0 Validar execucao, relatorios e premissas do runner Docker
  - [x] 5.1 Executar `docker compose config` para validar a estrutura do Compose.
  - [x] 5.2 Executar `docker compose build tests` para validar a construcao da imagem.
  - [x] 5.3 Executar `docker compose run --rm tests` com a API local disponivel ou ambiente configurado.
  - [x] 5.4 Confirmar que a execucao usa JDK 21 dentro do container.
  - [x] 5.5 Confirmar que as variaveis do `.env` chegam aos testes.
  - [x] 5.6 Confirmar que os relatorios HTML, JSON e XML sao gerados em `target/cucumber-reports/` no host.
  - [x] 5.7 Registrar no README ou nas observacoes da task qualquer falha causada por divergencia de contrato da API, sem alterar o escopo do Docker para contornar essas falhas.
  - [x] 5.8 Confirmar que nenhum servico extra de backend, banco, seed ou mock foi incluido no Compose.

  Observacao da validacao: `docker compose config` passou com apenas o servico `tests`; `docker compose build tests` construiu a imagem; `docker compose run --rm tests mvn -v` confirmou Maven 3.9.9 e Java 21.0.7; as variaveis do `.env` chegaram ao container; e os relatorios foram gerados em `target/cucumber-reports/`. A execucao completa da suite chegou a API e rodou, mas terminou com 3 falhas de contrato ja conhecidas: home sem `X-Citizen-Id` retornou `500`, profile sem `X-Citizen-Id` retornou `500`, e geocoding com endereco invalido retornou `200`. A task principal 5.0 permanece aberta ate a validacao completa passar sem divergencias de contrato da API.
