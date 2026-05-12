# language: pt
Funcionalidade: Perfil do cidadao
  Como consumidor autenticado da API
  Quero consultar meu perfil
  Para visualizar meus dados cadastrais e nivel atual

  Cenario: Consultar o perfil do cidadao autenticado
    Dado que existe um cidadao configurado para os testes
    Quando consulto o perfil do cidadao
    Entao a resposta deve ter status 200
    E a resposta deve ser JSON
    E o perfil deve pertencer ao cidadao configurado
    E o perfil deve exibir os dados obrigatorios do cidadao

  Cenario: Rejeitar consulta do perfil sem identificacao do cidadao
    Quando consulto o perfil sem informar o cidadao
    Entao a resposta deve ter status de requisicao nao autorizada ou invalida
    E a resposta deve informar que a identificacao do cidadao e obrigatoria quando esse contrato estiver disponivel
