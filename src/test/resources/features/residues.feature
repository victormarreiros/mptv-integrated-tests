# language: pt
Funcionalidade: Opcoes de filtro de residuos
  Como consumidor autenticado da API
  Quero consultar categorias de residuos
  Para filtrar pontos de coleta por tipo de descarte

  Cenario: Consultar opcoes de filtro de residuos
    Dado que existe um cidadao configurado para os testes
    Quando consulto as opcoes de filtro de residuos
    Entao a resposta deve ter status 200
    E a resposta deve ser JSON
    E as opcoes de filtro devem conter categorias de residuos
