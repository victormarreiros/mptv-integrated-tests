# language: pt
Funcionalidade: Pontos de coleta
  Como consumidor autenticado da API
  Quero consultar pontos de coleta
  Para encontrar locais adequados para descarte de residuos

  Cenario: Consultar detalhe de ponto de coleta por ID
    Dado que existe um ponto de coleta configurado para os testes
    Quando consulto o detalhe do ponto de coleta configurado
    Entao a resposta deve ter status 200
    E a resposta deve ser JSON
    E o ponto de coleta deve corresponder ao ID configurado
    E o ponto de coleta deve exibir nome, residuos aceitos e localizacao

  Cenario: Buscar pontos de coleta por proximidade
    Dado que existem coordenadas configuradas para os testes
    Quando busco pontos de coleta proximos
    Entao a resposta deve ter status 200
    E a resposta deve ser JSON
    E a busca deve retornar a localizacao pesquisada
    E a busca deve retornar pontos e o ponto mais proximo

  Cenario: Rejeitar consulta de ponto de coleta inexistente
    Quando consulto um ponto de coleta inexistente
    Entao a resposta deve ter status 404
    E a resposta deve informar que o recurso nao foi encontrado quando esse contrato estiver disponivel

  Cenario: Rejeitar busca por proximidade com parametros ausentes
    Quando busco pontos de coleta proximos sem informar coordenadas
    Entao a resposta deve ter status 400
    E a resposta deve informar que os parametros de busca sao obrigatorios quando esse contrato estiver disponivel

  Cenario: Rejeitar busca por proximidade com parametros invalidos
    Quando busco pontos de coleta proximos com parametros invalidos
    Entao a resposta deve ter status 400
    E a resposta deve informar que os parametros de busca sao invalidos quando esse contrato estiver disponivel
