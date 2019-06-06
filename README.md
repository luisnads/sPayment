# Sistema da Folha de Pagamentos.

Projeto compilado com JDK 12.0.1, escrito no IntelliJ.

## Instruções da execução do Sistema 
Comece informando o dia em que o programa está sendo rodado no formato “02/12” e logo em seguida informando o dia da semana em minúsculo com acentos, hifens (ex.: domingo, terça-feira, quarta-feira, sábado).

Informar os valores decimais com “.” (ex.: 1903.40).
O Sistema não calcula o calendário para anos bissextos, somente anos normais.

Quando for informado um menu de opções, informar o número correspondente ao desejado.

Quando for informado uma porcentagem de taxa ou similar, inserir em formato decimal da porcentagem.

Funcionários não são pagos em dias que não são úteis.

Não podem ser feitas Agendas de Pagamentos para dias de sábados e domingos.

### Operações do Sistema
#### Operação 1 – Adição de um funcionário
Deve-se informar o que for pedido.
Quando for informado um menu de opções, informar o número correspondente.

Por default, o funcionário não pertence ao sindicato, não tem ID sindical, não tem taxa sindical e o método de pagamento é Depósito em Conta Bancária.

#### Operação 2 – Remoção de um funcionário

Deve se informar o ID de um funcionário existente, caso contrário, irá retornar um erro.

#### Operação 3 – Lançamento de um Cartão de Ponto.
Deve se informar o ID de um funcionário existente, caso contrário, irá retornar um erro.

Não informar o cartão de ponto de um funcionário que não seja um horista.

Informar os horários de entrada e saída no formato “15:30”.

#### Operação 4 – Lançar um Resultado de Venda.
Deve se informar o ID de um funcionário existente, caso contrário, irá retornar um erro.
Não informar o resultado de venda para um funcionário que não seja um comissionado.

#### Operação 5 – Lançar uma taxa de serviço.
Deve se informar o ID Sindical de um funcionário, obviamente pertencente ao sindicato.

*A taxa de serviço é retirada do total do funcionário antes do desconto da taxa sindical.

#### Operação 6 – Alterar os detalhes de um funcionário.

Deve se informar o ID de um funcionário existente, caso contrário, irá retornar um erro.
Informar uma opção válida do menu quando for pedido (número, S, N).

#### Operação 7 – Rodar a folha de pagamento.
*A folha de pagamento informará os funcionários que serão pagos no dia de acordo com o calendário, mesmo que o funcionário tenha sido adicionado um dia antes do seu pagamento (ex.: semanal 1 sexta, e ele tenha sido adicionado na quinta), ele será pago.

*A taxa sindical é retirada em cima do salário que o funcionário receberá, já sendo aplicada a taxa de serviços (ex.: o salário são R$ 1000, houve uma taxa de R$ 900, serão pagos R$ 810.00).

#### Operação 8 – Undo/Redo.

#### Operação 9 – Agenda de Pagamento
O funcionário deve escolher uma opção válida em letras minúsculas.

#### Operação 10 – Criação de uma nova agenda de Pagamento.

#### Operação 0 – Sair.

