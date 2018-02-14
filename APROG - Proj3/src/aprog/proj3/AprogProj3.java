/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aprog.proj3;

import java.util.Calendar;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JFileChooser;

/**
 *
 * @author Jorge Gabriel Azevedo 1160929
 * @author André Oliveira 1161260
 *
 * @version 3.0
 * @project name Health Plus
 *
 *
 * Informação sobre o funcionamento do software: Leia o ficheiro readme.txt,
 * localizado na raíz da pasta deste ficheiro para saber mais informação sobre
 * algumas funcionalidades deste software.
 *
 */
public class AprogProj3 {

    /**
     * @param args the command line arguments
     */    //Falg dos tratamentos e do sexo
    static int servFlag = 0, genderFlag = 0;

    //Variaveis que irão guardar os dados anuais
    static float medF = 0, medM = 0, tncF = 0, tcF = 0, otF = 0, tncM = 0, tcM = 0, otM = 0;
    static int tncFCPfANO = 0, tncCRPfANO = 0, tcFCLfANO = 0, tcCRPfANO = 0, tncFCPmANO = 0, tncCRPmANO = 0, tcFCLmANO = 0, tcCRPmANO = 0;
    static int tSexoM = 0, tSexoF = 0, maisSexoF = 0, pagPartic = 0, pagConv = 0;

    public static void main(String[] args) {

        Calendar cal = Calendar.getInstance();

        int continueDay = 1; //variavel de controlo do ciclo
        int operation = -1; //Operação selecionada no menu
        int days = 0, currentDay = 1;
        int yearMin = 1970; //Ano no qual o timestamp é 1
        int yearMax = cal.get(Calendar.YEAR) - 1; //Só podemos oerar até ao ano anterior ao que esta a decorrer
        int exit = 0; //variavel para fechar o programa
        String currentDate;
        
        //Mostra mensagem de boas vindas
        JOptionPane.showMessageDialog(null, "Bem-vindo ao Health Plus\nAo utilizar este software concorda com os termos de utilização e proteção de dados\n\n\nCopyright © 2016 Gabriel Azevedo (1160929 ISEP) e André Oliveira (1161260 ISEP).\nAll rights reserved.", "Health Plus", JOptionPane.INFORMATION_MESSAGE);
        
        //so agora é que podemos criar as variaveis finais do Ano e dos dias
        final int YEAR = getYear(yearMin, yearMax);
        final int DAYS = getDays(YEAR);

        //Define os 2 vetores que vamos utilizar
        int[][] dayData = new int[2][13]; //[sexo][Tratamento]
        int[][][] yearData = new int[DAYS][2][13]; //[dia][sexo][Tratamento]

        for (currentDay = 1; currentDay <= DAYS; currentDay++) { //Vamos precorrer os dias do ano

            //reset vetor do dia
            for (int i = 0; i <= 12; i++) {
                //vamos colocar a zero todos os elementos contadores do vetor dayData, para cada sexo
                dayData[0][i] = 0;
                dayData[1][i] = 0;
            }

            //obter a string da data
            currentDate = getDate(YEAR, currentDay, false);

            do { //Vamos tratar os dados de cada paciente deste dia
                //mostra o menu
                operation = JOptionPane.showOptionDialog(
                        null,
                        "Data: " + currentDate + "\nSelecione uma das opções",
                        "Menu",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Adicionar Paciente", "Ver Info do Dia", "Finalizar o Dia"}, //O valor que a variavel vai obter vai ser o indice correspondente da opção neste vetor
                        "Adicionar Paciente");//Botão pre-definido

                switch (operation) {
                    case 0:
                        //Adcionar um novo paciente
                        getPatientsData(dayData);
                        //Vamos continuar neste dia
                        continueDay = 0;
                        break;

                    case 1:
                        //Apresenta a info de hoje
                        showDayInfo(false, currentDate, dayData);
                        //Apenas mostramos informação, devemos continuar neste dia
                        continueDay = 0;
                        break;

                    case 2:
                        //Vamos guardar toda a informação já recolhida
                        saveDayData(currentDay, dayData, yearData);
                        //Apresenta a info de hoje
                        showDayInfo(true, currentDate, dayData);
                        //Podemos terminar facilmente este dia
                        continueDay = 1;
                        break;

                    case -1:
                        //utilizador pretende fechar o programa
                        exit = JOptionPane.showConfirmDialog(null, "Tem a certeza que pretende fechar o programa?\nQauisquer dados já inseridos serão inrecuperaveis!");
                        if (exit == JOptionPane.OK_OPTION) {
                            //fecha o software dando return
                            return;
                        } else {
                            //O user pensou melhor e prefere continuar a usar este fantastico software
                            continueDay = 0;
                        }

                        break;
                    default:
                        continueDay = 0;
                        break;
                }

            } while (continueDay != 1); //No showConfirmDialog o "não" é a opção 1 logo trabalhamos o código para esta "realidade"
        }
        //Finalizamos o ano
        showYearInfo();

        saveToFile(yearData, YEAR, DAYS);

        /*para efeitos de debug
         System.out.println(Arrays.deepToString(yearData));*/
    }

    /*
     **********************************************************************************************************************
     ***************************************************Metodos************************************************************
     **********************************************************************************************************************
     */
    //Obtem o maior ano que pode ser analizado
    static int getYear(int yearMin, int yearMax) {
        int year;
        String inputData;
        
        //Vamos ler o ano em questão
        inputData = JOptionPane.showInputDialog("Indique o ano a operar:");

        //Tenta fazer o cast para int. se não resultar dá um alerta
        try {
            year = Integer.parseInt(inputData);//convertemos a String que recebemos num inteiro
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Introduza um valor numerico!",
                    "Erro!",
                    JOptionPane.ERROR_MESSAGE);
            year = 0; //Valor errado para o year
        }

        while (year < yearMin || year > yearMax) { //O valor nãoé reconhecido
            JOptionPane.showMessageDialog(
                    null,
                    "Valor do ano não reconhecido! Introduza um valor entre " + yearMin + " e " + yearMax + "!",
                    "Erro!",
                    JOptionPane.ERROR_MESSAGE);
            //Voltamos a ler
            inputData = JOptionPane.showInputDialog("Indique o ano a operar:");
            try {
                year = Integer.parseInt(inputData);//convertemos a String que recebemos num inteiro
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Introduza um valor numerico!",
                        "Erro!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return year;
    }

    /**
     * ***********************************************************************************************************
     */
    static int getDays(int year) {
        int days;

        if (year % 4 == 0) { //
            days = 366;
        } else {
            days = 365;
        }
        return days;
    }

    /**
     * ***********************************************************************************************************
     */
    //Obter uma string com a data do dia
    public static String getDate(int year, int day, boolean shortDateFormat) {
        NumberFormat formatter = new DecimalFormat("00");

        // Define os vetores a utilizar
        int[][] monthsDays = {
            {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365},
            {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366}
        };
        String[][] monthsNames = {
            {null, "janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"},
            {null, "jan", "fev", "mar", "abr", "mai", "jun", "jul", "ago", "set", "out", "nov", "dez"}
        };
        // Define as variaveis necessarias
        int leapYear = 0, shortDate = 0, i = 0;
        String date = "";
        //Verifica se o ano é bissexto
        if (year % 4 == 0) {
            leapYear = 1;
        } else {
            leapYear = 0;
        }
        //Verificar o formato da date
        if (shortDateFormat == true) {
            shortDate = 1;
        } else {
            shortDate = 0;
        }
        // escreve a data
        for (i = 1; i <= 12; i++) {
            if (day > monthsDays[leapYear][i - 1] && day <= monthsDays[leapYear][i]) {
                date = formatter.format(day - monthsDays[leapYear][i - 1]) + " " + monthsNames[shortDate][i] + " " + year;
            }
        }
        return date;
    }

    /**
     * ***********************************************************************************************************
     */
    //mostrar a informação do dia
    public static void showDayInfo(boolean endDay, String currentDay, int[][] info) {
        //Variaveis que vão guardar dados diarios
        float tratTotalDiaF = 0, tratTotalDiaM = 0, percTNC = 0, percTC = 0, percOT = 0, percFCLf = 0, percCRPm = 0;
        String finalInfo = "", extraInfo = "";

        if (endDay == false) {
            extraInfo = " (provisório)";
        }

        //percentagem dos pacientes que requisitaram tratamentos especificos
        if (info[1][0] > 0) {
            percFCLf = (float) info[1][9] * 100 / info[1][0];
        }
        if (info[0][0] > 0) {
            percCRPm = (float) info[0][9] * 100 / info[0][0];
        }

        // percentagem dos tratamentos femininos e masculinos
        tratTotalDiaF = info[1][1] + info[1][2] + info[1][3] + info[1][4] + info[1][5] + info[1][6] + info[1][7] + info[1][8];
        tratTotalDiaM = info[0][1] + info[0][2] + info[0][3] + info[0][4] + info[0][5] + info[0][6] + info[0][7] + info[0][8];

        if ((tratTotalDiaM + tratTotalDiaF) > 0) { //Apenas fazemos a divisão se tivermos pelo menos 1 tratamento
            percTNC = (info[0][1] + info[1][1] + info[0][2] + info[1][2] + info[0][3] + info[1][3]) * 100 / (tratTotalDiaM + tratTotalDiaF);
            percTC = (info[0][4] + info[1][4] + info[0][5] + info[1][5]) * 100 / (tratTotalDiaM + tratTotalDiaF);
            percOT = (info[0][6] + info[1][6] + info[0][7] + info[1][7] + info[0][8] + info[1][8]) * 100 / (tratTotalDiaM + tratTotalDiaF);
        }

        //Apresentaçãoo dos valores
        finalInfo = "[INFORMAÇÃOO DO DIA: " + currentDay + "]" + extraInfo + "\n"
                + "Precentagem de Tratamentos Não Cirugicos: " + percTNC + "%\n"
                + "Precentagem de Tratamentos Cirugicos: " + percTC + "%\n"
                + "Precentagem de Outros Tratamentos: " + percOT + "%\n"
                + "---------------------------------------------------------------------------------------\n"
                + "Pacientes do sexo Masculino: " + info[0][0] + "\nPacientes do sexo Feminino: " + info[1][0] + "\n"
                + "---------------------------------------------------------------------------------------\n"
                + percFCLf + "% das pacientes do sexo Feminino fizeram trat. faciais\n"
                + "---------------------------------------------------------------------------------------\n"
                + percCRPm + "% dos pacientes do sexo Masculino fizeram trat. corporais";

        JOptionPane.showMessageDialog(null, finalInfo);
    }

    /**
     * ***********************************************************************************************************
     */
    //mostrar a informação do ano
    public static void showYearInfo() {
        String finalYearInfo = "";

        if ((tSexoF + tSexoM) > 0) {
            medF = tSexoF * 100 / (tSexoF + tSexoM);
            medM = tSexoM * 100 / (tSexoF + tSexoM);
        }

        finalYearInfo = "[RESUMO DO ANO]\n"
                + "Em " + maisSexoF + " dias a UMCE atendeu mais pacientes do sexo feminino do que masculino\n"
                + "Durante este periodo " + medF + "% das visitas foram do sexo feminino e os restantes " + medM + "% foram do sexo masculino\n"
                + "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n"
                + "Tipos de tratamento mais frequente por sexo:\n"
                + "Sexo Masculino:\n";
        //Tratamentos não cirurgicos do sexo masculino
        if (tncFCPmANO > tncCRPmANO) {
            finalYearInfo += "O tratamento não cirurgico mais requisitado foi \"Facial e Cuidados com Pele\" com " + tncFCPmANO + " consultas\n";
        } else if (tncFCPmANO < tncCRPmANO) {
            finalYearInfo += "O tratamento não cirurgico mais requisitado foi \"Corporal\" com " + tncCRPmANO + " consultas\n";
        } else {
            finalYearInfo += "Os tratamentos não cirurgico \"Corporal\" e \"Facial e Cuidados com Pele\" tiveram o mesmo numero de consultas\n";
        }

        //Tratamentos cirurgicos do sexo masculino
        if (tcFCLmANO > tcCRPmANO) {
            finalYearInfo += "O tratamento cirurgico mais requisitado foi \"Facial e Cuidados com Pele\" com " + tcFCLmANO + " consultas\n";
        } else if (tcFCLmANO < tcCRPmANO) {
            finalYearInfo += "O tratamento cirurgico mais requisitado foi \"Corporal\" com " + tcCRPmANO + " consultas\n";
        } else {
            finalYearInfo += "Os tratamentos cirurgico \"Corporal\" e \"Facial e Cuidados com Pele\" tiveram o mesmo numero de consultas\n";
        }

        finalYearInfo += "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n";

        finalYearInfo += "Sexo Feminino:\n";
        //Tratamentos não cirurgicos do sexo feminino
        if (tncFCPfANO > tncCRPfANO) {
            finalYearInfo += "O tratamento não cirurgico mais requisitado foi \"Facial e Cuidados com Pele\" com " + tncFCPfANO + " consultas\n";
        } else if (tncFCPfANO < tncCRPfANO) {
            finalYearInfo += "O tratamento não cirurgico mais requisitado foi \"Corporal\" com " + tncCRPfANO + " consultas\n";
        } else {
            finalYearInfo += "Os tratamentos não cirurgico \"Corporal\" e \"Facial e Cuidados com Pele\" tiveram o mesmo numero de consultas\n";
        }

        //Tratamentos cirurgicos do sexo feminino
        if (tcFCLfANO > tcCRPfANO) {
            finalYearInfo += "O tratamento cirurgico mais requisitado foi \"Facial e Cuidados com Pele\" com " + tcFCLfANO + " consultas\n";
        } else if (tcFCLfANO < tcCRPfANO) {
            finalYearInfo += "O tratamento cirurgico mais requisitado foi \"Corporal\" com " + tcCRPfANO + " consultas\n";
        } else {
            finalYearInfo += "Os tratamentos cirurgico \"Corporal\" e \"Facial e Cuidados com Pele\" tiveram o mesmo numero de consultas\n";
        }

        finalYearInfo += "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n";

        //tipo de pagamento mais frequente
        if (pagPartic > pagConv) {
            finalYearInfo += "O tipo de pagamento mais frequente foi PARTICULAR\n";
        } else if (pagPartic < pagConv) {
            finalYearInfo += "O tipo de pagamento mais frequente foi CONVENCIONADO\n";
        } else {
            finalYearInfo += "O numero de tratamentos pagos por PARTICULARES foi igual ao numero de tratamentos pagos por CONVENCAO\n";
        }

        finalYearInfo += "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n";

        //Total pago
        finalYearInfo += "Este ano os pacientes do sexo masculino pagaram no total:\n"
                + "" + tncM * 80 + "€ por Tratamentos não cirurgicos\n"
                + "" + tcM * 110 + "€ por Tratamentos cirurgicos\n"
                + "" + otM * 95 + "€ por Outros Tratamentos\n"
                + "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n"
                + "Este ano as pacientes do sexo feminino pagaram no total:\n"
                + "" + tncF * 80 + "€ por Tratamentos não cirurgicos\n"
                + "" + tcF * 110 + "€ por Tratamentos cirurgicos\n"
                + "" + otF * 95 + "€ por Outros Tratamentos\n";

        JOptionPane.showMessageDialog(null, finalYearInfo);
    }

    /**
     * ***********************************************************************************************************
     */
    //Obter a informação do paciente e seus tratamentos
    public static void getPatientsData(int[][] info) {

        //Variaveis necessárias
        int gender = -1, servAp = 0, subServAp = 0, continuePatientFile = 0, pagConvencao = 0, saveDataFlag = 0;
        String inputData;
        String servs[] = {"Tratamento Não Cirurgíco", "Tratamento Cirurgíco", "Outras Áreas de Tratamento"};
        String subServ[][] = {
            {"Facial e Cuidados com a pele", "Corporal", "Laser"},
            {"Facial", "Corporal"},
            {"Tratamento de Varizes", "Tratamento de Obsidade", "Tratamento Dentário"}
        };

        //Vamos restaurar o valor da nossa Flag dos tratamentos
        servFlag = 0;
        genderFlag = 0;

        //Obter Sexo do paciente
        do {
            gender = JOptionPane.showOptionDialog(
                    null,
                    "Selecione o Sexo",
                    "Paciente",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Masculino", "Feminino"}, //O valor que a variavel vai obter vai ser o indice correspondente da opção neste vetor
                    "Masculino");//Botão pre-definido
        } while (gender != 0 && gender != 1);

        do {
            //Obter o tratamento aplicado
            inputData = (String) JOptionPane.showInputDialog(
                    null,
                    "Indique o Serviço Aplicado", //String mensaje
                    "Serviço Aplicado",//String título
                    JOptionPane.QUESTION_MESSAGE, //Icono por defecto
                    null, //Icono personalizado
                    servs,//Object[] ==> Vector con valores
                    servs[0]//Valor por defecto seleccionado
            );

            //vamos tentar analizar a opção selecionada. Se for um NULL saimos deste metodo
            try {
                switch (inputData) {
                    case "Tratamento Não Cirurgíco":
                        servAp = 0;
                        break;
                    case "Tratamento Cirurgíco":
                        servAp = 1;
                        break;
                    case "Outras Áreas de Tratamento":
                        servAp = 2;
                        break;
                }
            } catch (NullPointerException e) {
                return;
            }
            //Obter o subtratamento aplicado
            inputData = (String) JOptionPane.showInputDialog(
                    null,
                    "Indique o Sub-serviço Aplicado", //String mensaje
                    "Serviço Aplicado",//String título
                    JOptionPane.QUESTION_MESSAGE, //Icono por defecto
                    null, //Icono personalizado
                    subServ[servAp],//Object[] ==> Vector con valores
                    subServ[servAp][0]//Valor por defecto seleccionado
            );

            //tentaremos analizar a opção a cima como a cima
            try {
                switch (inputData) {
                    case "Facial e Cuidados com a pele":
                    case "Facial":
                    case "Tratamento de Varizes":
                        subServAp = 0;
                        break;
                    case "Corporal":
                    case "Tratamento de Obsidade":
                        subServAp = 1;
                        break;
                    case "Laser":
                    case "Tratamento Dentário":
                        subServAp = 2;
                        break;
                }
            } catch (NullPointerException e) {
                return;
            }

            //Obter o tipo de pagamento
            do {
                pagConvencao = JOptionPane.showOptionDialog(
                        null,
                        "Selecione o tipo de pagamento",
                        "Pagamento",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Particular", "Convenção"}, //O valor que a variavel vai obter vai ser o indice correspondente da opção neste vetor
                        "Particular");//Botão pre-definido

                //se for convenção mostra as opções disponiveis, se for particular fecha o ciclo se não dá erro e pede a informação
                switch (pagConvencao) {
                    case 0:
                        //pagConvencao já tem o valor que queremos, podemos sair
                        break;
                    case 1:
                        //perguntamos qual a convenção
                        pagConvencao = JOptionPane.showOptionDialog(
                                null,
                                "Qual Convenção?",
                                "Pagamento",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                new Object[]{"ADSE", "Seguro", "Cancelar"}, //O valor que a variavel vai obter vai ser o indice correspondente da opção neste vetor
                                "ADSE");//Botão pre-definido
                        switch (pagConvencao) {
                            case 0:
                                //Selecionou ADSE
                                pagConvencao = 1;
                                break;
                            case 1:
                                //Selecionou Seguro
                                pagConvencao = 2;
                                break;
                            default:
                                //Cancelou ou carregou no botão "X", volta ao botão inicial
                                pagConvencao = 1000;
                                break;
                        }
                        break;
                    default:
                        //mostra uma mensagem de erro e forçar o reínio de ciclo
                        JOptionPane.showMessageDialog(
                                null,
                                "Selecione uma das opções para continuar!",
                                "Erro!",
                                JOptionPane.ERROR_MESSAGE);
                        pagConvencao = 1000;
                        break;
                }

            } while (pagConvencao != 0 && pagConvencao != 1 && pagConvencao != 2); //pagConvencao != 0 && pagConvencao != 1 && pagConvencao != 2

            //Guarda os dados
            savePatientsData(gender, servAp, subServAp, pagConvencao, info);

            //Pergunta sobre um novo tratamento
            continuePatientFile = JOptionPane.showConfirmDialog(null, "Adicionar mais informação ao ficheiro deste paciente?");
        } while (continuePatientFile != 1); //Colocamos as opções em conformidade com o resultado do JOptionPane.showConfirmDialog

    }

    /**
     * ***********************************************************************************************************
     */
    //Guardar os dados do paciente, processa-os e guarda-os
    public static void savePatientsData(int gender, int servAp, int subServAp, int pagConvencao, int[][] dayInfo) {
        //Pergunta se pretende guardar esta informação
        String info = "", confirmationData = "", gen = "", service = "", subsurvice = "", payment = "";

        //Vamos converter as informações que temos numericas em texto apenas para as apresentar
        if (gender == 0) {
            gen = "Masculino";
        } else {
            gen = "Feminino";
        }
        switch ("" + servAp + subServAp) {
            case "00":
                service = "Tratamento Não Cirurgíco";
                subsurvice = "Facia e Cuidados com a Pele";
                break;
            case "01":
                service = "Tratamento Cirurgíco";
                subsurvice = "Corporal";
                break;
            case "02":
                service = "Outros Tratamento";
                subsurvice = "Tratamentos com Laser";
                break;
            case "10":
                service = "Tratamento Cirurgíco";
                subsurvice = "Facial";
                break;
            case "11":
                service = "Tratamento Cirurgíco";
                subsurvice = "Corporal";
                break;
            case "20":
                service = "Outros Tratamento";
                subsurvice = "Tratamento de Varizes";
                break;
            case "21":
                service = "Outros Tratamento";
                subsurvice = "Tratamento de Obsidade";
                break;
            case "22":
                service = "Outros Tratamento";
                subsurvice = "Tratamento Dentário";
                break;
        }
        switch (pagConvencao) {
            case 0:
                payment = "Particular";
                break;
            case 1:
                payment = "Convenção com ADSE";
                break;
            case 2:
                payment = "Convenção com Seguro";
                break;
        }
        info = "[RESUMO DO TRATAMENTO]:\n"
                + "   Sexo: " + gen + "\n"
                + "   Serviço aplicado: " + service + "\n"
                + "   Sub-serviço aplicado: " + subsurvice + "\n"
                + "   Tipo de pagamento: " + payment + "\n"
                + "Pretende guardar a informação a informação deste paciente?";

        int confirmation = JOptionPane.showConfirmDialog(null, info);

        //Analiza a resposta e age em conformidade
        if (confirmation == JOptionPane.OK_OPTION) {
            //se o sexo deste paciente ainda não tiver sido guardado, guarda-o
            if (genderFlag == 0) {
                //guarda no vetor dayData
                dayInfo[gender][0]++;
                genderFlag = 1;
            }

            //passa as informações para o vetor dayData
            switch ("" + servAp + subServAp) {
                case "00":
                    dayInfo[gender][1]++;
                    break;
                case "01":
                    dayInfo[gender][2]++;
                    break;
                case "02":
                    dayInfo[gender][3]++;
                    break;
                case "10":
                    dayInfo[gender][4]++;
                    break;
                case "11":
                    dayInfo[gender][5]++;
                    break;
                case "20":
                    dayInfo[gender][6]++;
                    break;
                case "21":
                    dayInfo[gender][7]++;
                    break;
                case "22":
                    dayInfo[gender][8]++;
                    break;
            }

            //passa informações sobres as percentagem para o vetor dayData, apenas se a Flag estiver a 0 (Zero)
            confirmationData = "" + gender + servAp + subServAp + servFlag;
            switch (confirmationData) {
                case "0010":
                case "0110":
                    //sexo masculino + (TNC / TC) + sub-trat. Corporal + Falg a 0
                    dayInfo[gender][9]++;
                    servFlag = 1;
                    break;
                case "1000":
                case "1100":
                    //sexo feminino + (TNC / TC) + sub-trat. Facial + Falg a 0
                    dayInfo[gender][9]++;
                    servFlag = 1;
                    break;
            }
            /*
             passa os dados do pagamento para o array. temos 1 espaço reservado para os pag particulares, 1 para pag ADES e 1 para paga Seguro
             * para achar qual a posição adeqada a guardar os dados basta somar ao valor de pagConvencao 10.
             * p.e. particular: pagConvencao = 0 -> pagConvencao + 10 = 10 -> posição do vetor é a 10
             */
            dayInfo[gender][10 + pagConvencao]++;

        }
    }

    /**
     * ***********************************************************************************************************
     */
    public static void saveDayData(int day, int[][] dayData, int[][][] yearData) {
        //guarda toda a informação de dayInfo em yearInfo
        for (int i = 0; i <= 12; i++) {
            //a estrutura de dayData e yearData são semelhantes, então a transição de dados é muito facil
            yearData[day - 1][0][i] = dayData[0][i];
            yearData[day - 1][1][i] = dayData[1][i];
        }
        //guarda o numero total dos tratamento por sexo
        tncFCPmANO += dayData[0][1];
        tncCRPmANO += dayData[0][2];
        tcFCLmANO += dayData[0][4];
        tcCRPmANO += dayData[0][5];
        tncFCPfANO += dayData[1][1];
        tncCRPfANO += dayData[1][2];
        tcFCLfANO += dayData[1][4];
        tcCRPfANO += dayData[1][5];

        //total de pacientes de cada sexo
        tSexoF = dayData[0][0];
        tSexoM = dayData[1][0];

        //verifica o º de pacientes de cada sexo
        if (dayData[1][0] > dayData[0][0]) {
            maisSexoF++;
        }

        //dados do pagamento
        pagPartic += dayData[0][10] + dayData[1][10]; //soma os pagamentos particulares de homens e mulheres e incrementa o a variavel global com esse valor
        pagConv += dayData[0][11] + dayData[1][11] + dayData[0][12] + dayData[1][12];

        //Guarda a quantidade de tratamentos sexo feminino no ano
        tncF += dayData[1][1] + dayData[1][2] + dayData[1][3];
        tcF += dayData[1][4] + dayData[1][5];
        otF += dayData[1][6] + dayData[1][7] + dayData[1][8];

        //Guarda a quantidade de tratamentos sexo masculino no ano
        tncM += dayData[0][1] + dayData[0][2] + dayData[0][3];
        tcM += dayData[0][4] + dayData[0][5];
        otM += dayData[0][6] + dayData[0][7] + dayData[0][8];

    }

    public static void saveToFile(int[][][] yearData, int year, int days) {
        NumberFormat formatter = new DecimalFormat("00000");

        int save = 0, i = 0;

        //utilizador pretende fechar o programa
        save = JOptionPane.showConfirmDialog(null, "Pretende guardar uma cópia dos dados em ficheiro de texto (.txt)?");
        if (save == JOptionPane.OK_OPTION) {
            //texto que vamos escrever
            String temp = "";
            String lineSepartor = "\r\n"; //separador de linha. Vamos utilizar o separador do Windows, porque tanto o Linux como o OSX são compativeis com este separador, mas o contrário já não é valido
            String text = "Relatório dos tratamentos efetuados durante os " + days + " dias de " + year + lineSepartor + lineSepartor
                    + "|             |  N Total  |                                                HOMEM                                                      |                                                  MULHER                                                   |" + lineSepartor
                    + "|             |    de     |-----------------------------------------------------------------------------------------------------------+-----------------------------------------------------------------------------------------------------------|" + lineSepartor
                    + "|     Dia     | Pacientes |  N de   | N Total |          TNC          |       TC      |          OT           |       Pagamento       |  N de   | N Total |          TNC          |       TC      |          OT           |       Pagamento       |" + lineSepartor
                    + "|             | Atendidos |  Total  |Pacientes|-----------------------+---------------+-----------------------+-----------------------+  Total  |Pacientes|-----------------------+---------------+-----------------------+-----------------------|" + lineSepartor
                    + "|             |  No Dia   |Pacientes|Corporal |  FCL  |  CRP  |  TCL  |  FCL  |  CRP  |  TV   |  TO   |  TD   | Part. | ADSE  | Seg.  |Pacientes| Facial  |  FCL  |  CRP  |  TCL  |  FCL  |  CRP  |  TV   |  TO   |  TD   | Part. | ADSE  | Seg.  |" + lineSepartor;

            //Vamos precorrer yaerData e guardar tudo na String text. No final guardamos o resultado no ficheiro que o utilizador vai escolher
            for (i = 0; i <= days - 1; i++) {

                text += "|-------------+-----------+---------+---------+-------+-------+-------+-------+-------+-------+-------+-------+-------+-------+-------+---------+---------+-------+-------+-------+-------+-------+-------+-------+-------+-------+-------+-------|" + lineSepartor;
                text += "| " + getDate(year, i + 1, true) + " |   " + formatter.format(yearData[i][0][0] + yearData[i][1][0]) + "   |  " + formatter.format(yearData[i][0][0]) + "  |  " + formatter.format(yearData[i][0][1]) + "  | " + formatter.format(yearData[i][0][2]) + " | " + formatter.format(yearData[i][0][3]) + " | " + formatter.format(yearData[i][0][4]) + " | " + formatter.format(yearData[i][0][5]) + " | " + formatter.format(yearData[i][0][6]) + " | " + formatter.format(yearData[i][0][7]) + " | " + formatter.format(yearData[i][0][8]) + " | " + formatter.format(yearData[i][0][9]) + " | " + formatter.format(yearData[i][0][10]) + " | " + formatter.format(yearData[i][0][11]) + " | " + formatter.format(yearData[i][0][12]) + " |  " + formatter.format(yearData[i][1][0]) + "  |  " + formatter.format(yearData[i][1][1]) + "  | " + formatter.format(yearData[i][1][2]) + " | " + formatter.format(yearData[i][1][3]) + " | " + formatter.format(yearData[i][1][4]) + " | " + formatter.format(yearData[i][1][5]) + " | " + formatter.format(yearData[i][1][6]) + " | " + formatter.format(yearData[i][1][7]) + " | " + formatter.format(yearData[i][1][8]) + " | " + formatter.format(yearData[i][1][9]) + " | " + formatter.format(yearData[i][1][10]) + " | " + formatter.format(yearData[i][1][11]) + " | " + formatter.format(yearData[i][1][12]) + " |" + lineSepartor;

            }
            text +=   "|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|"
                    + lineSepartor + lineSepartor
                    + "***************************************************************************************************************************************************************************************************************************************************"
                    + lineSepartor + lineSepartor;

            //Vamos guardar ainda a informação que foi mostrada no fim do programa
            text += "RESUMO FINAL DO ANO" + lineSepartor
                    + "Em " + maisSexoF + " dias a UMCE atendeu mais pacientes do sexo feminino do que masculino" + lineSepartor
                    + "Durante este periodo " + medF + "% das visitas foram do sexo feminino e os restantes " + medM + "% foram do sexo masculino" + lineSepartor
                    + "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" + lineSepartor
                    + "Tipos de tratamento mais frequente por sexo:" + lineSepartor
                    + "Sexo Masculino:" + lineSepartor;
            //Tratamentos não cirurgicos do sexo masculino
            if (tncFCPmANO > tncCRPmANO) {
                text += "O tratamento não cirurgico mais requisitado foi \"Facial e Cuidados com Pele\" com " + tncFCPmANO + " consultas" + lineSepartor;
            } else if (tncFCPmANO < tncCRPmANO) {
                text += "O tratamento não cirurgico mais requisitado foi \"Corporal\" com " + tncCRPmANO + " consultas" + lineSepartor;
            } else {
                text += "Os tratamentos não cirurgico \"Corporal\" e \"Facial e Cuidados com Pele\" tiveram o mesmo numero de consultas" + lineSepartor;
            }

            //Tratamentos cirurgicos do sexo masculino
            if (tcFCLmANO > tcCRPmANO) {
                text += "O tratamento cirurgico mais requisitado foi \"Facial e Cuidados com Pele\" com " + tcFCLmANO + " consultas" + lineSepartor;
            } else if (tcFCLmANO < tcCRPmANO) {
                text += "O tratamento cirurgico mais requisitado foi \"Corporal\" com " + tcCRPmANO + " consultas" + lineSepartor;
            } else {
                text += "Os tratamentos cirurgico \"Corporal\" e \"Facial e Cuidados com Pele\" tiveram o mesmo numero de consultas" + lineSepartor;
            }

            text += "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" + lineSepartor;

            text += "Sexo Feminino:" + lineSepartor;
            //Tratamentos não cirurgicos do sexo feminino
            if (tncFCPfANO > tncCRPfANO) {
                text += "O tratamento não cirurgico mais requisitado foi \"Facial e Cuidados com Pele\" com " + tncFCPfANO + " consultas" + lineSepartor;
            } else if (tncFCPfANO < tncCRPfANO) {
                text += "O tratamento não cirurgico mais requisitado foi \"Corporal\" com " + tncCRPfANO + " consultas" + lineSepartor;
            } else {
                text += "Os tratamentos não cirurgico \"Corporal\" e \"Facial e Cuidados com Pele\" tiveram o mesmo numero de consultas" + lineSepartor;
            }

            //Tratamentos cirurgicos do sexo feminino
            if (tcFCLfANO > tcCRPfANO) {
                text += "O tratamento cirurgico mais requisitado foi \"Facial e Cuidados com Pele\" com " + tcFCLfANO + " consultas" + lineSepartor;
            } else if (tcFCLfANO < tcCRPfANO) {
                text += "O tratamento cirurgico mais requisitado foi \"Corporal\" com " + tcCRPfANO + " consultas" + lineSepartor;
            } else {
                text += "Os tratamentos cirurgico \"Corporal\" e \"Facial e Cuidados com Pele\" tiveram o mesmo numero de consultas" + lineSepartor;
            }

            text += "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" + lineSepartor;

            //tipo de pagamento mais frequente
            if (pagPartic > pagConv) {
                text += "O tipo de pagamento mais frequente foi PARTICULAR" + lineSepartor;
            } else if (pagPartic < pagConv) {
                text += "O tipo de pagamento mais frequente foi CONVENCIONADO" + lineSepartor;
            } else {
                text += "O numero de tratamentos pagos por PARTICULARES foi igual ao numero de tratamentos pagos por CONVENCAO" + lineSepartor;
            }

            text += "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" + lineSepartor;

            //Abre a janela de dialogo para escolher o ficheiro
            JFileChooser chooser = new JFileChooser(); //cria o objeto do dialogo
            int retrival = chooser.showSaveDialog(null); //mostra o dialogo

            //vamos operar o ficheiro selecionado, se algum ficheiro foir selecionado        
            if (retrival == JFileChooser.APPROVE_OPTION) {
                try {
                    //escreve tudo no ficheiro
                    FileWriter fw = new FileWriter(chooser.getSelectedFile()); //cria o objeto do ficheiro selecionado
                    fw.write(text.toString()); //apaga todo o conteudo e escreve a string
                    fw.close(); //fecha o ficheiro

                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("erro!");
                } finally {
                    System.out.println("supostamente já acabou");
                }
            }
        } else {
            return;
        }
    }

}
