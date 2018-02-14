Informa��o sobre o funcionamento do software
---------------------------------------------------------------------------------
Recolha da Informa��o di�ria
A informa��o � guardada no vetor inteiro dayData[2][9]. Controi-se assim um
"tabela" que guarda o numero de pacientes que requisitou cada um dos servi�os
separados por sexo

  [2]: Sexo do paciente. (0): Sexo Masculino (1): Sexo Feminino
  [x]: (0): N� total de pacientes deste sexo 
       (1): N� total de Tratamentos N�o Cirurgicos do tipo Facial e Cuidados com a Pele
       (2): N� total de Tratamentos N�o Cirurgicos do tipo Corporal
       (3): N� total de Tratamentos N�o Cirurgicos do tipo Com Laser
       (4): N� total de Tratamentos Cirurgicos do tipo Facial
       (5): N� total de Tratamentos Cirurgicos do tipo Corporal
       (6): N� total de Outros Tratamentos do tipo Tratamento de Varizes
       (7): N� total de Outros Tratamentos do tipo Tratamento de Obsidade
       (8): N� total de Outros Tratamentos do tipo Tratamentos Dent�rios
       (9): Pacientes do sexo masculino que fizeram tratamentos corporais
            Pacientes do sexo feminino que fizeram tratamentos faciais
       (10):N� total de tratamentos pagos por particular
       (11):N� total de tratamentos pagos por conven��o com ADSE
       (12):N� total de tratamentos pagos por conven��o com Seguro

----------------------------------------------------------------------------------
Recolha da Informa��o anual
A informa��o � guardada no vetor inteiro yearData[YEAR][2][x]. Este vetor
tridimencional, vai guardar todos os dados diarios ao longo do ano. Apenas ser�
efetuada a adi��o de informa��o a este vetor, quando o dia estiver concluido.
  Explica��o das dimens�es
  [YEAR]: constante que indica o numero de dias que este ano tem
  [2]: Sexo do paciente. (0): Sexo Masculino (1): Sexo Feminino
  [x]: (0): N� total de pacientes deste sexo 
       (1): N� total de Tratamentos N�o Cirurgicos do tipo Facial e Cuidados com a Pele
       (2): N� total de Tratamentos N�o Cirurgicos do tipo Corporal
       (3): N� total de Tratamentos N�o Cirurgicos do tipo Com Laser
       (4): N� total de Tratamentos Cirurgicos do tipo Facial
       (5): N� total de Tratamentos Cirurgicos do tipo Corporal
       (6): N� total de Outros Tratamentos do tipo Tratamento de Varizes
       (7): N� total de Outros Tratamentos do tipo Tratamento de Obsidade
       (8): N� total de Outros Tratamentos do tipo Tratamentos Dent�rios
       (9): Pacientes do sexo masculino que fizeram tratamentos corporais
            Pacientes do sexo feminino que fizeram tratamentos faciais
       (10):N� total de tratamentos pagos por particular
       (11):N� total de tratamentos pagos por conven��o com ADSE
       (12):N� total de tratamentos pagos por conven��o com Seguro
----------------------------------------------------------------------------------
