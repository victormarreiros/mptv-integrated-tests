# language: pt
Funcionalidade: Dicas de descarte
  Como consumidor autenticado da API
  Quero consultar dicas de descarte
  Para aprender como descartar residuos corretamente

  Cenario: Listar dicas disponiveis
    Dado que existe um cidadao configurado para os testes
    Quando consulto as dicas disponiveis
    Entao a resposta deve ter status 200
    E a resposta deve ser JSON
    E a lista de dicas deve conter itens com identificacao, titulo e slug

  Cenario: Consultar detalhe de dica por slug
    Dado que existe uma dica configurada para os testes
    Quando consulto o detalhe da dica configurada
    Entao a resposta deve ter status 200
    E a resposta deve ser JSON
    E o detalhe da dica deve corresponder ao slug configurado
    E o detalhe da dica deve conter titulo e conteudo

  Cenario: Rejeitar consulta de dica inexistente
    Quando consulto uma dica inexistente
    Entao a resposta deve ter status 404
    E a resposta deve informar que o recurso nao foi encontrado quando esse contrato estiver disponivel
