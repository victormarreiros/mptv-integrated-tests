# language: pt
Funcionalidade: Geocoding de endereco
  Como consumidor autenticado da API
  Quero converter um endereco em coordenadas
  Para buscar pontos de coleta proximos

  Cenario: Consultar geocoding por endereco configurado
    Dado que existe um endereco configurado para os testes
    Quando consulto o geocoding do endereco configurado
    Entao a resposta deve ter status 200
    E a resposta deve ser JSON
    E o geocoding deve retornar o endereco formatado
    E o geocoding deve retornar latitude e longitude

  Cenario: Rejeitar geocoding sem endereco
    Quando consulto o geocoding sem informar endereco
    Entao a resposta deve ter status 400
    E a resposta deve informar que o endereco e obrigatorio quando esse contrato estiver disponivel

  Cenario: Rejeitar geocoding com endereco invalido
    Quando consulto o geocoding com endereco invalido
    Entao a resposta deve ter status de endereco nao encontrado ou requisicao invalida
    E a resposta deve informar que o endereco nao pode ser geocodificado quando esse contrato estiver disponivel
