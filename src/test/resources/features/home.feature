# language: pt
Funcionalidade: Home do cidadao
  Como consumidor autenticado da API
  Quero consultar a minha home
  Para visualizar meu resumo e minhas acoes principais

  Cenario: Consultar a home do cidadao autenticado
    Dado que existe um cidadao configurado para os testes
    Quando consulto a home do cidadao
    Entao a resposta deve ter status 200
    E a resposta deve ser JSON
    E a home deve pertencer ao cidadao configurado
    E a home deve exibir o resumo e as acoes principais do cidadao

  Cenario: Rejeitar consulta da home sem identificacao do cidadao
    Quando consulto a home sem informar o cidadao
    Entao a resposta deve ter status de requisicao nao autorizada ou invalida
    E a resposta deve informar que a identificacao do cidadao e obrigatoria quando esse contrato estiver disponivel
