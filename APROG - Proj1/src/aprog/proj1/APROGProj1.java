/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aprog.proj1;

import java.util.Scanner;

/**
 *
 * @author Jorge Gabriel Azevedo 1160929 ISEP
 *
 * Informação sobre o funcionamento do software Para um funcionamento mais
 * simples da aplicação, algumas referencias textuais (Strings) foram
 * substituidas por uma representção numerica. Organização dos serviços e
 * sub-serviços: Existem duas variaveis do tipo inteiro, servAp e subServAp. Ao
 * juntar a informação destas duas variaveis é possivel defenir o tratamento
 * aplicado ao paciente (0)Tratamentos Não Cirúrgicos - TNC (0)FACIAL E CUIDADOS
 * COM A PELE - FCP (1)CORPORAL - CRP (2)TRATAMENTOS COM LASER - TCL
 *
 * (1)Tratamentos Cirúrgicos - TC (0)FACIAL - FCL (1)CORPORAL - CRP
 *
 * (2)Outras Áreas de Tratamento - OT (0)TRATAMENTO DE VARIZES - TV
 * (1)TRATAMENTO DE OBESIDADE - TO (2)TRATAMENTO DENTÁRIO - TD Com os valores
 * servAp = 1 e subServAp = 0, "indica-se ao software" a informação "Tipo de
 * Serviço CIRURGICO, Subtipo FACIAL"
 * -----------------------------------------------------------------------------
 * Tipo de pagamento Variavel Pagamento: int PagConvencao Convenção (1)ADSE
 * (2)Seguro (0)Particular Neste caso usa-se uma lógica semelhante. A variavel
 * guarda o valor do pagamento por convenção (pagConvencao). Se o pagamento for
 * particular a afirmação "Pagamento por convenção" é falsa (0), se não é
 * verdadeira (!=0). Assim definem-se dois valores distintos, diferentes de
 * zero, para representar esta situação 1 para ADSE e 2 para Seguro.
 * -----------------------------------------------------------------------------
 * Guardar a informação do tratamento No final de cada tratamento é perguntado
 * se o utilizadir pretende guardar os dados daquele tratamento. Ao mesmo tempo
 * que se guarda o primeiro tratamento guarda-se o sexo do paciente. Para evitar
 * que este dado se repetido erradamente usa-se a variavel saveDataFlag para
 * controlar esta situação.
 * -----------------------------------------------------------------------------
 * Armazenamento de dados Existe um conjunto de variaveis que formam a lista de
 * todas as informações recolhidas pelo sistema
 * -----------------------------------------------------------------------------
 */
public class APROGProj1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Variaveis
        final int PERIODO = 365; //Dias que o software deve trabalhar
        int servAp, subServAp, servFlag, continuePatientFile, continueDay, currentDay, pagConvencao, validateData, saveDataFlag;

        //Variaveis que irão guardar toda a informação sobre pacientes sexo masculino
        int tncFCPm, tncCRPm, tncTCLm, tcFCLm, tcCRPm, otTVm, otTOm, otTDm, sexoM;

        //Variaveis que irão guardar toda a informação sobre pacientes sexo feminino
        int tncFCPf, tncCRPf, tncTCLf, tcFCLf, tcCRPf, otTVf, otTOf, otTDf, sexoF;

        //Variaveis que vão guardar dados diarios
        float tratTotalDiaF, tratTotalDiaM, percTNC, percTC, percOT, fclF, crpM;

        //Variaveis que irão guardar os dados anuais
        float medF = 0, medM = 0, tncF = 0, tcF = 0, otF = 0, tncM = 0, tcM = 0, otM = 0;
        int tncFCPfANO = 0, tncCRPfANO = 0, tcFCLfANO = 0, tcCRPfANO = 0, tncFCPmANO = 0, tncCRPmANO = 0, tcFCLmANO = 0, tcCRPmANO = 0;
        int tSexoM = 0, tSexoF = 0, maisSexoF = 0, pagPartic = 0, pagConv = 0;

        char sexo = ' ';
        String inputTemp = "", compareData = ""; //String que receberá todas as entrada feitas. Só depois de tratados os dados dessa String é que se guarda a info nas variaveis definitivas

        //Preparação do Objeto de Leitura
        Scanner keyboard = new Scanner(System.in);

        //Código da aplicação
        for (currentDay = 1; currentDay <= PERIODO; currentDay++) { //Vamos precorrer os dias do ano 
            
            //Antes de iniciar este dia, vamos repor o valor original de cada variavel utilizada
            servAp = subServAp = servFlag = continuePatientFile = continueDay = pagConvencao = validateData = saveDataFlag = tncFCPm = tncCRPm = tncTCLm = tcFCLm = tcCRPm = otTVm = otTOm = otTDm = sexoM = tncFCPf = tncCRPf = tncTCLf = tcFCLf = tcCRPf = otTVf = otTOf = otTDf = sexoF = 0;
            tratTotalDiaF = tratTotalDiaM = percTNC = percTC = percOT = fclF = crpM = 0;
            sexo = ' ';
            inputTemp = "";
            compareData = "";
            
            System.out.println("[DIA " + currentDay + "]");
            do {//Vamos tratar os dados de cada paciente deste dia
                
                servFlag = 0; //Começamos novo paciente, a flag volta a zero
                
                //Vamos pedir o Sexo do Paciente
                System.out.print("Indique o Sexo do Paciente[M/F]: ");
                inputTemp = keyboard.nextLine();

                while (!inputTemp.equalsIgnoreCase("M") && !inputTemp.equalsIgnoreCase("F")) { //Se o utilizador introduzir um valor que não é valido, mostra-se o Erro e pede-se nova inserção, quantas vezes for necessário
                    System.out.print("ERRO:O sexo Expecificado não é válido\nIntroduza \"M\" (sem aspas) para um paciente do sexo masculino\nou \"F\" (sem aspas) para um paciente do sexo feminino\nIndique o Sexo do Paciente[M/F]: ");
                    inputTemp = keyboard.nextLine();
                }

                //Temos a certeza que o sexo do pacinete é válido, logo podemos uniformaliza-lo
                inputTemp = inputTemp.toUpperCase(); //Vamos colocar o sexo em maiusculas
                sexo = inputTemp.charAt(0); //Já podemos guardar o input na variavel final
                if (sexo == 'M') {
                    System.out.println("Paciente do sexo masculino");
                    sexoM++;
                } else {
                    System.out.println("Paciente do sexo feminino");
                    sexoF++;
                }

                System.out.println("[===========================================================================================================================]"); //Divisória

                do {//Vamos ler todos os tratamentos de cada paciente
                    
                    do {
                        System.out.print("Tipo de tratamento\nIndique o tipo de tratamento introduzindo a sua sigla, conforme a lista a baixo.\nTratamentos Não Cirúrgicos\t-\tTNC\nTratamentos Cirúrgicos\t\t-\tTC\nOutras Áreas de Tratamento\t-\tOT\nTipo de tratamento: ");
                        inputTemp = keyboard.nextLine();
                        inputTemp = inputTemp.toUpperCase(); //Vamos trabalhar so com letras maiusculas para não termos problemas

                        switch (inputTemp) { //Vamos agora tratar dos dados que foram enviados. Se o utilizador não enviar uma string com sentido voltamos ao inicio
                            case "TNC":
                                servAp = 0;
                                System.out.print("Tipo de tratamento: Tratamento Não Cirúrgico\nSelecione da seguinte lista um dos subtipos de tratamento:\nFACIAL E CUIDADOS COM A PELE \t- FCP\nCORPORAL\t\t\t- CRP\nTRATAMENTOS COM LASER\t\t- TCL\nSubtipo de tratamento: ");
                                inputTemp = keyboard.nextLine();
                                inputTemp = inputTemp.toUpperCase(); //Vamos trabalhar so com letras maiusculas para não termos problemas

                                switch (inputTemp) { //Vamos la verificar este input
                                    case "FCP":
                                        System.out.println("Sub-serviço aplicado: Facial e Cuidados com a Pele");
                                        subServAp = 0;
                                        validateData = 1; //apos isto estamos em condições de avançar sem erros
                                        break;
                                    case "CRP":
                                        System.out.println("Sub-serviço aplicado: Corporal");
                                        subServAp = 1;
                                        validateData = 1;
                                        break;
                                    case "TCL":
                                        System.out.println("Sub-serviço aplicado: Tratamento com Laser");
                                        subServAp = 2;
                                        validateData = 1;
                                        break;
                                    default:
                                        validateData = 0; //Ha algum erro vamos voltar ao inicio
                                        System.out.println("ERRO: Tipo de sub-serviço desconhecido. Verifique os dados e introduza de novo.");
                                        break;
                                }

                                break;

                            case "TC":
                                servAp = 1;
                                System.out.print("Tipo de tratamento: Tratamento Cirúrgico\nSelecione da seguinte lista um dos subtipos de tratamento:\nFACIAL\t\t-\tFCL\nCORPORAL\t-\tCRP\nSubtipo de tratamento: ");
                                inputTemp = keyboard.nextLine();
                                inputTemp = inputTemp.toUpperCase(); //Vamos trabalhar so com letras maiusculas para não termos problemas

                                switch (inputTemp) { //Vamos la verificar este input
                                    case "FCL":
                                        System.out.println("Sub-serviço aplicado: Facial");
                                        subServAp = 0;
                                        validateData = 1; //apos isto estamos em condições de avançar sem erros
                                        break;
                                    case "CRP":
                                        System.out.println("Sub-serviço aplicado: Corporal");
                                        subServAp = 1;
                                        validateData = 1;
                                        break;
                                    default:
                                        validateData = 0; //Ha algum erro vamos voltar ao inicio
                                        System.out.println("ERRO: Tipo de sub-serviço desconhecido. Verifique os dados e introduza de novo.");
                                        break;
                                }

                                break;

                            case "OT":
                                servAp = 2;
                                System.out.print("Tipo de tratamento: Outras Áreas de Tratamento\nSelecione da seguinte lista um dos subtipos de tratamento:\nTRATAMENTO DE VARIZES\t-\tTV\nTRATAMENTO DE OBESIDADE\t-\tTO\nTRATAMENTO DENTÁRIO\t-\tTD\nSubtipo de tratamento: ");
                                inputTemp = keyboard.nextLine();
                                inputTemp = inputTemp.toUpperCase(); //Vamos trabalhar so com letras maiusculas para não termos problemas

                                switch (inputTemp) { //Vamos la verificar este input
                                    case "TV":
                                        System.out.println("Sub-serviço aplicado: Tratamento de Varizes");
                                        subServAp = 0;
                                        validateData = 1; //apos isto estamos em condições de avançar sem erros
                                        break;
                                    case "TO":
                                        System.out.println("Sub-serviço aplicado: Tratamento de Obsidade");
                                        subServAp = 1;
                                        validateData = 1;
                                        break;
                                    case "TD":
                                        System.out.println("Sub-serviço aplicado: Tratamento Dentario");
                                        subServAp = 2;
                                        validateData = 1;
                                        break;
                                    default:
                                        validateData = 0; //Ha algum erro vamos voltar ao inicio
                                        System.out.println("ERRO: Tipo de sub-serviço desconhecido. Verifique os dados e introduza de novo.");
                                        break;
                                }

                                break;

                            default:
                                validateData = 0; //Erro ao introduir o tipo
                                System.out.println("ERRO: Tipo de serviço desconhecido. Verifique os dados e introduza de novo.");
                                break;
                        }
                    } while (validateData == 0);

                    System.out.println("[===========================================================================================================================]"); //Divisória

                    pagConvencao = 1000; //vamos dar à variavel de controlo um valor absurdo para não termos problemas 
                    do { //vamos definir o tipo de pagamento 
                        System.out.print("O pagamento é por convenção?[S/N]");
                        inputTemp = keyboard.nextLine();
                        if (inputTemp.equalsIgnoreCase("S")) { //O pagamento é por convenção. Temos de saber é qual
                            System.out.print("Convenção com ADSE?[S/N] ");
                            inputTemp = keyboard.nextLine();
                            if (inputTemp.equalsIgnoreCase("S")) { //Convenção com ADSE
                                pagConvencao = 1;
                            } else if (inputTemp.equalsIgnoreCase("N")) { //Convenção com seguro
                                pagConvencao = 2;
                            } else { //Se não é "S" nem "N" é erro
                                System.out.println("Erro: Resposta não reconhecida");
                            }
                        } else if (inputTemp.equalsIgnoreCase("N")) { //o pagamento é particular
                            pagConvencao = 0;
                        } else { //Se não é "S" nem "N" é erro
                            System.out.println("Erro: Resposta não reconhecida");
                        }
                    } while (pagConvencao < 0 || pagConvencao > 2);

                    System.out.println("[===========================================================================================================================]"); //Divisória
                    //Vamos verificar se o utilizador quer guardar estes dados
                    //futuramente será possivel apresentar os dados de cada paciente, para confirmar se é mesmo para guardar estes dados
                    System.out.print("Pretende guardar estes dados? [s/N]");
                    inputTemp = keyboard.nextLine();
                    if (inputTemp.equalsIgnoreCase("S")) {

                        //Vamos criar uma string para usar na comparação
                        compareData = "" + servAp + subServAp + sexo;

                        switch (compareData) { //Vamos ao nosso array guardar a informação
                            case "00M":
                                tncFCPm++;
                                tncFCPmANO++;
                                break;
                            case "00F":
                                tncFCPf++;
                                tncFCPfANO++;
                                servFlag = 1; //vamos por esta variavel =1 para controlar o tratamento desta paciente
                                break;
                            case "01M":
                                tncCRPm++;
                                tncCRPmANO++;
                                servFlag = 1; //vamos por esta variavel =1 para controlar o tratamento deste paciente
                                break;
                            case "01F":
                                tncCRPf++;
                                tncCRPfANO++;
                                break;
                            case "02M":
                                tncTCLm++;
                                break;
                            case "02F":
                                tncTCLf++;
                                break;
                            case "10M":
                                tcFCLm++;
                                tcFCLmANO++;
                                break;
                            case "10F":
                                tcFCLf++;
                                tcFCLfANO++;
                                servFlag = 1; //vamos por esta variavel =1 para controlar o tratamento desta paciente
                                break;
                            case "11M":
                                tcCRPm++;
                                tcCRPmANO++;
                                servFlag = 1; //vamos por esta variavel =1 para controlar o tratamento deste paciente
                                break;
                            case "11F":
                                tcCRPf++;
                                tcCRPfANO++;
                                break;
                            case "20M":
                                otTVm++;
                                break;
                            case "20F":
                                otTVf++;
                                break;
                            case "21M":
                                otTOm++;
                                break;
                            case "21F":
                                otTOf++;
                                break;
                            case "22M":
                                otTDm++;
                                break;
                            case "22F":
                                otTDf++;
                                break;
                        }

                        //Guarda do tipo de pagamento
                        if (pagConvencao > 0) {
                            pagConv++;
                        } else {
                            pagPartic++;
                        }

                        System.out.println("Dados Guardados com sucesso!");
                    } else {
                        System.out.println("Inormação rejeitada!");
                    }

                    System.out.println("[===========================================================================================================================]"); //Divisória
                    //Vamos verificar se o utilizador quer continuar neste paciente ou passar a um novo
                    System.out.print("Pretende adicionar mais informação ao ficheiro deste utente? [s/N]");
                    inputTemp = keyboard.nextLine();
                    if (inputTemp.equalsIgnoreCase("S")) { //Não sabemos como esta a variavel de controlo portanto temos os dois ramos do IF, mas a opção por definição é o NÃO
                        continuePatientFile = 1;
                    } else {
                        continuePatientFile = 0;
                        //verificar os tratarmentos feitos
                        if (sexo == 'F' && servFlag == 1) {
                            fclF++;
                        } else if (sexo == 'M' && servFlag == 1) {
                            crpM++;
                        }
                    }

                    //Limpa os dados das variaveis utilizadas anteriormente
                } while (continuePatientFile == 1);

                System.out.println("[===========================================================================================================================]"); //Divisória
                //Vamos verificar se o utilizador quer adiconar mais info neste dia
                System.out.print("Pretende adicionar mais sobre este dia? [s/N]");
                inputTemp = keyboard.nextLine();
                System.out.println("[===========================================================================================================================]"); //Divisória
                if (inputTemp.equalsIgnoreCase("S")) { //Não sabemos como esta a variavel de controlo portanto temos os dois ramos do IF, mas a opção por definição é o NÃO
                    continueDay = 1;
                } else {
                    continueDay = 0;
                    //Percentagem dos tratamentos
                    tratTotalDiaF = tncFCPf + tncCRPf + tncTCLf + tcFCLf + tcCRPf + otTVf + otTOf + otTDf;
                    tratTotalDiaM = tncFCPm + tncCRPm + tncTCLm + tcFCLm + tcCRPm + otTVm + otTOm + otTDm;
                    percTNC = (tncFCPm + tncFCPf + tncCRPm + tncCRPf + tncTCLm + tncTCLf) * 100 / (tratTotalDiaM + tratTotalDiaF);
                    percTC = (tcFCLm + tcFCLf + tcCRPm + tcCRPf) * 100 / (tratTotalDiaM + tratTotalDiaF);
                    percOT = (otTVm + otTVf + otTOm + otTOf + otTDm + otTDf) * 100 / (tratTotalDiaM + tratTotalDiaF);

                    //Guarda a quantidade de tratamentos sexo feminino
                    tncF += tncFCPf + tncCRPf + tncTCLf;
                    tcF += tcFCLf + tcCRPf;
                    otF += otTVf + otTOf + otTDf;

                    //Guarda a quantidade de tratamentos sexo masculino
                    tncM += tncFCPm + tncCRPm + tncTCLm;
                    tcM += tcFCLm + tcCRPm;
                    otM += otTVm + otTOm + otTDm;

                    //Guarda valores que vamos precisar mais para frente
                    tSexoF += sexoF;
                    tSexoM += sexoM;

                    //Percentagem de tratamentos faciais com pacientes do sexo feminino
                    if(sexoF > 0){ //apenas de tiverem sido feitos tratamento é que vamos mexer neste valor
                        fclF = fclF * 100 / sexoF;
                    }

                    //Percentagem de tratamentos corporais com pacientes do sexo masculino
                    if(sexoM > 0){ //apenas de tiverem sido feitos tratamento é que vamos mexer neste valor
                        crpM = crpM * 100 / sexoM;
                    }

                    //Vamos verificar se foram atendidos mais homens ou mulheres
                    if (sexoF > sexoM) {
                        maisSexoF++;
                    }

                    //Apresentação dos valores
                    System.out.println("[INFORMAÇÃO DO DIA: " + currentDay + "]\n"
                            + "Precentagem de Tratamentos Não Cirugicos: " + percTNC + "%\n"
                            + "Precentagem de Tratamentos Cirugicos: " + percTC + "%\n"
                            + "Precentagem de Outros Tratamentos: " + percOT + "%\n"
                            + "--------------------------------------------------------------------------------\n"
                            + "Pacientes do sexo Masculino: " + sexoM + "\nPacientes do sexo Feminino: " + sexoF + "\n"
                            + "--------------------------------------------------------------------------------\n"
                            + fclF + "% das pacientes do sexo Feminino fizeram trat. faciais\n"
                            + "--------------------------------------------------------------------------------\n"
                            + crpM + "% das pacientes do sexo Masculino fizeram trat. corporais");
                    System.out.println("[===========================================================================================================================]"); //Divisória
                }
                //Limpeza das variaveis diarias
            } while (continueDay == 1);

        }
        medF = tSexoF * 100 / (tSexoF + tSexoM);
        medM = tSexoM * 100 / (tSexoF + tSexoM);
        System.out.println("[RESUMO DO ANO]\n"
                + "Em " + maisSexoF + " dias a UMCE atendeu mais pacientes do sexo feminino do que masculino\n"
                + "Durante este periodo " + medF + "% das visitas foram do sexo feminino e os restantes " + medM + "% foram do sexo masculino\n"
                + "Tipos de tratamento mais frequente por sexo:\n"
                + "\t Sexo Masculino:");
        //Tratamentos não cirurgicos do sexo masculino
        if (tncFCPmANO > tncCRPmANO) {
            System.out.println("\t\tO tratamento não cirurgico mais requisitado foi \"Facial e Cuidados com Pele\" com " + tncFCPmANO + " consultas");
        } else if (tncFCPmANO < tncCRPmANO){
            System.out.println("\t\tO tratamento não cirurgico mais requisitado foi \"Corporal\" com " + tncCRPmANO + " consultas");
        }
        else{
            System.out.println("\t\tOs tratamentos não cirurgico \"Corporal\" e \"Facial e Cuidados com Pele\" tiveram o mesmo numero de consultas");
        }
        
        //Tratamentos cirurgicos do sexo masculino
        if (tcFCLmANO > tcCRPmANO) {
            System.out.println("\t\tO tratamento cirurgico mais requisitado foi \"Facial e Cuidados com Pele\" com " + tcFCLmANO + " consultas");
        } else if (tcFCLmANO < tcCRPmANO){
            System.out.println("\t\tO tratamento cirurgico mais requisitado foi \"Corporal\" com " + tcCRPmANO + " consultas");
        }
        else{
            System.out.println("\t\tOs tratamentos cirurgico \"Corporal\" e \"Facial e Cuidados com Pele\" tiveram o mesmo numero de consultas");
        }

        System.out.println("\t Sexo Feminino");
        //Tratamentos não cirurgicos do sexo feminino
        if (tncFCPfANO > tncCRPfANO) {
            System.out.println("\t\tO tratamento não cirurgico mais requisitado foi \"Facial e Cuidados com Pele\" com " + tncFCPfANO + " consultas");
        } else if (tncFCPfANO < tncCRPfANO){
            System.out.println("\t\tO tratamento não cirurgico mais requisitado foi \"Corporal\" com " + tncCRPfANO + " consultas");
        }
        else{
            System.out.println("\t\tOs tratamentos não cirurgico \"Corporal\" e \"Facial e Cuidados com Pele\" tiveram o mesmo numero de consultas");
        }
        
        //Tratamentos cirurgicos do sexo feminino
        if (tcFCLfANO > tcCRPfANO) {
            System.out.println("\t\tO tratamento cirurgico mais requisitado foi \"Facial e Cuidados com Pele\" com " + tcFCLfANO + " consultas");
        } else if (tcFCLfANO < tcCRPfANO){
            System.out.println("\t\tO tratamento cirurgico mais requisitado foi \"Corporal\" com " + tcCRPfANO + " consultas");
        }
        else{
            System.out.println("\t\tOs tratamentos cirurgico \"Corporal\" e \"Facial e Cuidados com Pele\" tiveram o mesmo numero de consultas");
        }

        //tipo de pagamento mais frequente
        if (pagPartic > pagConv) {
            System.out.println("O tipo de pagamento mais frequente foi PARTICULAR");
        } else if (pagPartic < pagConv) {
            System.out.println("O tipo de pagamento mais frequente foi CONVENCIONADO");
        } else {
            System.out.println("O numero de tratamentos pagos por PARTICULARES foi igual ao numero de tratamentos pagos por CONVENCAO");
        }

        //Total pago
        System.out.println("Este ano os pacientes do sexo masculino pagaram no total:\n"
                + "\t" + tncM * 80 + "€ por Tratamentos não cirurgicos\n"
                + "\t" + tcM * 110 + "€ por Tratamentos cirurgicos\n"
                + "\t" + otM * 95 + "€ por Tratamentos não cirurgicos\n"
                + "Este ano as pacientes do sexo feminino pagaram no total:\n"
                + "\t" + tncF * 80 + "€ por Tratamentos não cirurgicos\n"
                + "\t" + tcF * 110 + "€ por Tratamentos cirurgicos\n"
                + "\t" + otF * 95 + "€ por Tratamentos não cirurgicos\n");
    }

}
