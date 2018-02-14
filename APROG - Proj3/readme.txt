Informação sobre o funcionamento do software
---------------------------------------------------------------------------------
Recolha da Informação diária
A informação é guardada no vetor inteiro dayData[2][9]. Controi-se assim um
"tabela" que guarda o numero de pacientes que requisitou cada um dos serviços
separados por sexo

  [2]: Sexo do paciente. (0): Sexo Masculino (1): Sexo Feminino
  [x]: (0): Nº total de pacientes deste sexo 
       (1): Nº total de Tratamentos Não Cirurgicos do tipo Facial e Cuidados com a Pele
       (2): Nº total de Tratamentos Não Cirurgicos do tipo Corporal
       (3): Nº total de Tratamentos Não Cirurgicos do tipo Com Laser
       (4): Nº total de Tratamentos Cirurgicos do tipo Facial
       (5): Nº total de Tratamentos Cirurgicos do tipo Corporal
       (6): Nº total de Outros Tratamentos do tipo Tratamento de Varizes
       (7): Nº total de Outros Tratamentos do tipo Tratamento de Obsidade
       (8): Nº total de Outros Tratamentos do tipo Tratamentos Dentários
       (9): Pacientes do sexo masculino que fizeram tratamentos corporais
            Pacientes do sexo feminino que fizeram tratamentos faciais
       (10):Nº total de tratamentos pagos por particular
       (11):Nº total de tratamentos pagos por convenção com ADSE
       (12):Nº total de tratamentos pagos por convenção com Seguro

----------------------------------------------------------------------------------
Recolha da Informação anual
A informação é guardada no vetor inteiro yearData[YEAR][2][x]. Este vetor
tridimencional, vai guardar todos os dados diarios ao longo do ano. Apenas será
efetuada a adição de informação a este vetor, quando o dia estiver concluido.
  Explicação das dimensões
  [YEAR]: constante que indica o numero de dias que este ano tem
  [2]: Sexo do paciente. (0): Sexo Masculino (1): Sexo Feminino
  [x]: (0): Nº total de pacientes deste sexo 
       (1): Nº total de Tratamentos Não Cirurgicos do tipo Facial e Cuidados com a Pele
       (2): Nº total de Tratamentos Não Cirurgicos do tipo Corporal
       (3): Nº total de Tratamentos Não Cirurgicos do tipo Com Laser
       (4): Nº total de Tratamentos Cirurgicos do tipo Facial
       (5): Nº total de Tratamentos Cirurgicos do tipo Corporal
       (6): Nº total de Outros Tratamentos do tipo Tratamento de Varizes
       (7): Nº total de Outros Tratamentos do tipo Tratamento de Obsidade
       (8): Nº total de Outros Tratamentos do tipo Tratamentos Dentários
       (9): Pacientes do sexo masculino que fizeram tratamentos corporais
            Pacientes do sexo feminino que fizeram tratamentos faciais
       (10):Nº total de tratamentos pagos por particular
       (11):Nº total de tratamentos pagos por convenção com ADSE
       (12):Nº total de tratamentos pagos por convenção com Seguro
----------------------------------------------------------------------------------
Modelo da tabela
|             |  N Total  |                                                HOMEM                                                      |                                                  MULHER                                                   |
|             |    de     |-----------------------------------------------------------------------------------------------------------+-----------------------------------------------------------------------------------------------------------|
|     Dia     | Pacientes | N Total | N Total |          TNC          |       TC      |          OT           |       Pagamento       |  N de   | N Total |          TNC          |       TC      |          OT           |       Pagamento       |
|             | Atendidos |   de    |Pacientes|-----------------------+---------------+-----------------------+-----------------------+  Total  |Pacientes|-----------------------+---------------+-----------------------+-----------------------|
|             |   Hoje    |Pacientes|Corporal |  FCL  |  CRP  |  TCL  |  FCL  |  CRP  |  TV   |  TO   |  TD   | Part. | ADSE  | Seg.  |Pacientes| Facial  |  FCL  |  CRP  |  TCL  |  FCL  |  CRP  |  TV   |  TO   |  TD   | Part. | ADSE  | Seg.  |
|-------------+-----------+---------+---------+-------+-------+-------+-------+-------+-------+-------+-------+-------+-------+-------+---------+---------+-------+-------+-------+-------+-------+-------+-------+-------+-------+-------+-------|
| 00 abc 0000 |   00000   |  00000  |  00000  | 00000 | 00000 | 00000 | 00000 | 00000 | 00000 | 00000 | 00000 | 00000 | 00000 | 00000 |  00000  |  00000  | 00000 | 00000 | 00000 | 00000 | 00000 | 00000 | 00000 | 00000 | 00000 | 00000 | 00000 |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
