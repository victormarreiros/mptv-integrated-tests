# language: pt
Funcionalidade: Saude da API
  Como desenvolvedor
  Quero confirmar que a API esta disponivel
  Para executar os cenarios integrados com uma base pronta

  Cenario: API disponivel para execucao dos testes integrados
    Quando consulto a saude da API
    Entao a resposta deve ter status 200
    E a API deve indicar que esta disponivel
    E a resposta de saude deve respeitar o schema esperado
