package com.company;
import java.util.Scanner;

public class PaymentSystem {

    private static void showEmployees(String[][] employees) {
        int i = 0;
        while(employees[i][0] != null) {
            showEmployeeInfo(employees[i][0], employees);
            i++;
        }
    }
    private static void showEmployeeInfo(String id, String[][] employees) {
        System.out.println();
        int index = getIndex(id, employees);
        if(index == -1)
            System.out.println("Funcionário não encontrado!\n");
        else {
            System.out.printf("ID | %s\n", employees[index][0]);
            System.out.printf("Index | %d\n", index);
            System.out.printf("Nome | %s\n", employees[index][1]);
            System.out.printf("Agenda de Pagamento | %s\n\n", employees[index][5]);
        }
    }
    private static int getIndex(String id, String[][] employees) {
        int i = 0;
        while(employees[i][0] != null) {
            if(employees[i][0].equals(id))
                break;
            i++;
        }
        if(employees[i][0] == null)
            return -1;
        return i;
    }
    private static void getEmployeeType(Scanner input, int index, String[][] employees) {
        System.out.println("\nTipos de funcionário.");
        System.out.println("[1] Horista\n[2] Assalariado\n[3] Comissionado.\n");
        System.out.print("Insira o tipo do funcionário: ");
        employees[index][2] = input.nextLine();
        switch (Integer.parseInt(employees[index][2]))
        {
            case 1:
                System.out.print("Insira o valor da hora de trabalho do novo funcionário: ");
                employees[index][3] = input.nextLine();
                employees[index][4] = "None";
                employees[index][5] = "semanal 1 sexta";
                break;
            case 2:
                System.out.print("Insira o valor do salário mensal do novo funcionário: ");
                employees[index][3] = input.nextLine();
                employees[index][4] = "None";
                employees[index][5] = "mensal $";
                break;
            case 3:
                System.out.print("Insira o valor do salário mensal do novo funcionário: ");
                employees[index][3] = input.nextLine();
                System.out.print("Insira o valor da comissão do novo funcionário em decimal (ex.: 0.2 = 20%): ");
                employees[index][4] = input.nextLine();
                employees[index][5] = "semanal 2 sexta";
                break;
        }
    }
    private static void fillCalendary(int[][] calendary, int day, int month, String dayInfo) {
        int data = 0;
        switch (dayInfo) {
            case "domingo":
                data = 1;
                break;
            case "segunda-feira":
                data = 2;
                break;
            case "terça-feira":
                data = 3;
                break;
            case "quarta-feira":
                data = 4;
                break;
            case "quinta-feira":
                data = 5;
                break;
            case "sexta-feira":
                data = 6;
                break;
            case "sábado":
                data = 7;
                break;
        }
        while(month < 13) {
            while(day < 32) {
                if(((month%2) == 1 && month < 8)|| month == 8 || month == 10 || month == 12) {
                    calendary[month][day] = data;
                    data++;
                }
                else {
                    if(month == 2 && day > 28)
                        calendary[month][day] = -1;
                    else if(day == 31)
                        calendary[month][day] = -1;
                    else {
                        calendary[month][day] = data;
                        data++;
                    }
                }
                if(data == 8)
                    data = 1;
                day++;
            }
            day = 1;
            month++;
        }
    }
    private static void copyMatrix(String[][][] undo, String[][] employees, int undoCount) {
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 12; j++) {
                undo[i][j][undoCount] = employees[i][j];
            }
        }
    }
    private static void restoreMatrix(String[][] employees, String[][][] undo, int undoCount) {
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 12; j++) {
                employees[i][j] = undo[i][j][undoCount];
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=============== Seja bem-vindo ao Sistema Folha de Pagamento ===============\n");

        int id = 1, index = 0, iSchedule = 0, dayCount = 0, undoCount = 0, operation;
        int[] lastOp = new int[10];
        boolean check = true;
        Scanner input = new Scanner(System.in);
        String[][] employees = new String[100][12];
        String[][][] undo = new String[100][12][10];
        String[][][] redo = new String[100][12][10];
        String[] pSchedule = new String[20];

        int[][] calendary = new int[13][32];
        System.out.println("Insira data que o Sistema está sendo rodado inicialmente (ex.: 04/06/2019): ");
        String[] timeArrayInfo = input.nextLine().split("/");
        int day = Integer.parseInt(timeArrayInfo[0]);
        int month = Integer.parseInt(timeArrayInfo[1]);
        System.out.println("Insira o dia da semana que o Sistema está sendo rodado inicialmente em letras minúsculas: ");
        String dayInfo = input.nextLine();
        fillCalendary(calendary, day, month, dayInfo);

        //ID | Nome | Tipo | Salário | Comissão | Agenda de Pagamento | Sindicato | IDSindicato | TaxaSindicato | Endereço | TotalSalary | Método

        while(check) {
            System.out.printf("\nData: %d/%d\n\n", day, month);
            operation = header(input);
            if(operation > 0 && operation < 8) {
                lastOp[undoCount] = operation;
                copyMatrix(undo, employees, undoCount++);
                if(undoCount == 10)
                    undoCount = 0;
            }
            switch(operation) {
                case 1:
                    addEmployee(index++, id++, input, employees);
                    break;
                case 2:
                    removeEmployee(input, employees);
                    index--;
                    break;
                case 3:
                    hourlyCard(input, employees);
                    break;
                case 4:
                    sellResult(input, employees);
                    break;
                case 5:
                    serviceTax(input, employees);
                    break;
                case 6:
                    setNewInfo(input, employees);
                    break;
                case 7:
                    payRoll(day, month, employees, calendary, (dayCount-7));
                    day++;
                    dayCount++;
                    if(day > 31 || calendary[month][day] == -1) {
                        day = 1;
                        month++;
                    }
                    if(dayCount == 14)
                        dayCount = 0;
                    break;
                case 8:
                    System.out.println("[1] Undo | [2] Redo");
                    int uCheck = Integer.parseInt(input.nextLine());
                    if(uCheck == 1 && undoCount > 0) {
                        if(lastOp[undoCount-1] == 1) {
                            restoreMatrix(employees, undo, (undoCount-1));
                            index--;
                            id--;
                        }
                        else if(lastOp[undoCount-1] == 2) {
                            restoreMatrix(employees, undo, (undoCount-1));
                            index++;
                        }
                        else if(lastOp[undoCount-1] == 7) {
                            restoreMatrix(employees, undo, (undoCount-1));
                            if(day == 1) {
                                month--;
                                if(((month%2) == 1 && month < 8)|| month == 8 || month == 10 || month == 12) {
                                    day = 31;
                                }
                                else {
                                    if(month == 3)
                                        day = 28;
                                    else
                                        day = 30;
                                }
                            }
                            else {
                                day--;
                            }
                            dayCount--;
                        }
                        else {
                            restoreMatrix(employees, undo, undoCount);
                        }
                        undoCount--;
                        if(undoCount < 0)
                            undoCount = 9;
                    }

                    break;
                case 9:
                    setSchedule(input, pSchedule, employees);
                    break;
                case 10:
                    newSchedule(input, pSchedule, iSchedule);
                    iSchedule++;
                    break;
                case 11:
                    showEmployees(employees);
                    break;
                case 0:
                    check = false;
                    break;
            }
        }

    }
    private static int header(Scanner input) {
        System.out.println("[1] Adição de um novo funcionário.\n[2] Remoção de um funcionário.\n[3] Lançar um Cartão de Ponto.");
        System.out.println("[4] Lançar um resultado de venda.\n[5] Lançar uma taxa de serviço.\n[6] Alterar detalhes de um funcionário.");
        System.out.println("[7] Rodar a folha de pagamento para hoje.\n[8] Undo/Redo.\n[9] Agenda de Pagamento.\n[10] Criação de Novas Agendas de Pagamento.");
        System.out.println("[0] Sair.\n");
        System.out.print("Insira a operação deseja realizar: ");
        return Integer.parseInt(input.nextLine());
    }
    private static void addEmployee(int index, int id, Scanner input, String[][] employees) {
        employees[index][0] = Integer.toString(id);
        System.out.println();
        System.out.print("Insira o nome do novo funcionário: ");
        employees[index][1] = input.nextLine();
        getEmployeeType(input, index, employees);
        employees[index][6] = "Não";
        employees[index][7] = "None";
        employees[index][8] = "None";
        System.out.print("Insira o endereço do novo funcionário: ");
        employees[index][9]  = input.nextLine();
        if(employees[index][2].equals("2"))
            employees[index][10] = employees[index][3];
        else
            employees[index][10] = "0";
        employees[index][11] = "Depósito em conta bancária";
        System.out.println();
    }
    private static void removeEmployee(Scanner input, String[][] employees) {
        System.out.print("Insira o ID do funcionário que será removido: ");
        String ID = input.nextLine();
        int index = getIndex(ID, employees);
        if(index == -1) {
            System.out.println("Funcionário não encontrado!\n");
            return;
        }
        while(employees[index+1][0] != null) {
            for(int i = 0; i < 12; i++) {
                employees[index][i] = employees[index+1][i];
            }
            index++;
        }
        employees[index][0] = null;
    }
    private static void sellResult(Scanner input, String[][] employees) {

        System.out.print("Insira o ID do funcionário que lançará o resultado da venda: ");
        String ID = input.nextLine();
        int index = getIndex(ID, employees);
        if(index == -1)
            System.out.println("Funcionário não encontrado!\n");
        else {
            System.out.print("Insira o valor do resultado da venda: ");
            double value = Double.parseDouble(input.nextLine());

            employees[index][10] = Double.toString(value*(Double.parseDouble(employees[index][4]))
                    + Double.parseDouble(employees[index][10]));
        }
    }
    private static void hourlyCard(Scanner input, String[][] employees) {
        double extra = 0;
        System.out.print("Insira o ID do funcionário que lançará o cartão de ponto: ");
        String ID = input.nextLine();
        int index = getIndex(ID, employees);
        if(index == -1)
            System.out.println("Funcionário não encontrado!\n");
        else {
            System.out.println("Insira as informações do cartão de ponto no formato (16:32 (horas e minutos))");
            System.out.print("Horário de entrada do funcionário: ");
            String[] hoursIn = input.nextLine().split(":");
            System.out.print("Horário de saída do funcionário: ");
            String[] hoursOut = input.nextLine().split(":");
            int hEntrada = Integer.parseInt(hoursIn[0]);
            int hSaida = Integer.parseInt(hoursOut[0]);
            int hTotal = hSaida - hEntrada;
            double minTotal = (Integer.parseInt(hoursOut[1])-Integer.parseInt(hoursIn[1]))/60.0;
            if(minTotal < 0)
                minTotal *= -1;
            if(hTotal > 8)
                extra = (hTotal-8)*0.5*Double.parseDouble(employees[index][3]);
            double total = (minTotal + hTotal)*Double.parseDouble(employees[index][3]);

            employees[index][10] = Double.toString((total + Double.parseDouble(employees[index][10]) +  extra));
        }
    }
    private static void serviceTax(Scanner input, String[][] employees) {

        System.out.print("Insira o ID do funcionário que terá a taxa de serviço: ");
        String ID = input.nextLine();
        int index = getIndex(ID, employees);
        if(index == -1)
            System.out.println("Funcionário não encontrado!\n");
        else {
            System.out.print("Insira o valor da taxa de serviço: ");
            String tax = input.nextLine();

            employees[index][10] = Double.toString(Double.parseDouble(employees[index][10]) - Double.parseDouble(tax));
        }
    }
    private static void setNewInfo(Scanner input, String[][] employees) {
        int confirm;

        System.out.print("Insira o ID do funcionário que terá as informações alteradas: ");
        String ID = input.nextLine();
        int index = getIndex(ID, employees);
        if(index == -1)
            System.out.println("Funcionário não encontrado!\n");
        else {
            System.out.println("Deseja mudar o nome do funcionário? [0] Sim | [1] Não");
            confirm = Integer.parseInt(input.nextLine());
            if (confirm == 0) {
                System.out.print("Insira o novo nome do funcionário: ");
                employees[index][1] = input.nextLine();
            }

            System.out.println("Deseja mudar o tipo do funcionário? [0] Sim | [1] Não");
            confirm = Integer.parseInt(input.nextLine());
            if (confirm == 0) {
                getEmployeeType(input, index, employees);
            }

            System.out.println("Deseja mudar se o funcionário pertence ao sindicato? [0] Sim | [1] Não");
            confirm = Integer.parseInt(input.nextLine());
            if (confirm == 0) {
                System.out.println("O funcionário pertence ao sindicato? [S]im | [N]ão");
                if (input.nextLine().equals("S")) {
                    employees[index][6] = "Sim";
                    System.out.print("Insira o ID sindical do funcionário: ");
                    employees[index][7] = input.nextLine();
                    System.out.print("Insira a taxa sindical do funcionário em decimal (ex.: 0.2 = 20%): ");
                    employees[index][8] = input.nextLine();
                } else {
                    employees[index][6] = "Não";
                    employees[index][7] = "None";
                    employees[index][8] = "None";
                }
            }

            System.out.println("Deseja mudar o endereço do funcionário? [0] Sim | [1] Não");
            confirm = Integer.parseInt(input.nextLine());
            if (confirm == 0) {
                System.out.print("Insira o novo endereço do funcionário: ");
                employees[index][9] = input.nextLine();
            }

            System.out.println("Deseja mudar o método de pagamento do funcionário? [0] Sim | [1] Não");
            confirm = Integer.parseInt(input.nextLine());
            if (confirm == 0) {
                System.out.print("Insira o novo método de pagamento: ");
                employees[index][11] = input.nextLine();
            }
        }
    }
    private static void newSchedule(Scanner input, String[] pSchedule, int iSchedule) {
        System.out.print("Insira a nova agenda de pagamento: ");
        pSchedule[iSchedule] = input.nextLine();
    }
    private static void setSchedule(Scanner input, String[] pSchedule, String[][] employees) {
        int i = 0;
        System.out.print("Insira o ID do funcionário que terá a agenda de pagamento modificada: ");
        String ID = input.nextLine();
        int index = getIndex(ID, employees);

        System.out.println("As agendas de pagamento disponíveis são:");
        System.out.println("→ semanal 1 sexta\n→ mensal $\n→ semanal 2 sexta");
        while(i < 20 && pSchedule[i] != null) {
            System.out.printf("→ %s\n",pSchedule[i]);
            i++;
        }

        System.out.print("Insira nova agenda de pagamento desejada: ");
        employees[index][5] = input.nextLine();
    }
    private static String totalSalary(String[][] employees, int i) {
        String totalSalaryA;

        if(Integer.parseInt(employees[i][2]) == 3) {
            totalSalaryA = employees[i][3];
        }
        else {
            totalSalaryA = employees[i][10];
        }

        if(employees[i][6].equals("Sim")) {
            totalSalaryA = Double.toString((Double.parseDouble(totalSalaryA)
                    - Double.parseDouble(totalSalaryA)*Double.parseDouble(employees[i][8])));

        }
        return totalSalaryA;

    }
    private static void payEmployee(String[][] employees, double forPay, int i) {
        System.out.printf("O funcionário %s de ID [%s] recebeu %.2f via %s\n", employees[i][1],
                employees[i][0], forPay, employees[i][11]);
        if(Integer.parseInt(employees[i][2]) != 2)
            employees[i][10] = "0";
    }
    private static void auxPayRoll(String[][] employees, int i, int[][] calendary, int day, int month, String[] paySchedule) {
        double forPay = Double.parseDouble(totalSalary(employees, i));
        if(Integer.parseInt(employees[i][2]) != 1) {
            forPay *= Double.parseDouble(paySchedule[1])/4;
            if(Integer.parseInt(employees[i][2]) == 3)
                forPay += Double.parseDouble(employees[i][10]);
        }
        switch (paySchedule[2]) {
            case "segunda":
                if(calendary[month][day] == 2)
                    payEmployee(employees, forPay, i);
                break;
            case "terça":
                if(calendary[month][day] == 3)
                    payEmployee(employees, forPay, i);
                break;
            case "quarta":
                if(calendary[month][day] == 4)
                    payEmployee(employees, forPay, i);
                break;
            case "quinta":
                if(calendary[month][day] == 5)
                    payEmployee(employees, forPay, i);
                break;
            case "sexta":
                if(calendary[month][day] == 6)
                    payEmployee(employees, forPay, i);
                break;
        }
    }
    private static void payRoll(int day, int month, String[][] employees, int[][] calendary, int dayCount) {
        int i = 0, lDay = 0;
        for (int j = 31; j > 0; j--) {
            if (calendary[month][j] > 1 && calendary[month][j] < 7) {
                lDay = j;
                break;
            }
        }
        while(employees[i][0] != null) {
            String[] paySchedule = employees[i][5].split(" ");
            if(paySchedule[0].equals("mensal") && paySchedule[1].equals("$") && day == lDay) {
                System.out.printf("O funcionário %s de ID [%s] recebeu %.2f via %s\n", employees[i][1],
                        employees[i][0], Double.parseDouble(totalSalary(employees, i)), employees[i][11]);
            }
            else if(!paySchedule[1].equals("$") && paySchedule[0].equals("mensal") && day == Integer.parseInt(paySchedule[1])) {
                System.out.printf("O funcionário %s de ID [%s] recebeu %.2f via %s\n", employees[i][1],
                        employees[i][0], Double.parseDouble(totalSalary(employees, i)), employees[i][11]);
            }
            else if(paySchedule[0].equals("semanal")) {
                if(paySchedule[1].equals("1")) {
                    auxPayRoll(employees, i, calendary, day, month, paySchedule);
                }
                else if(paySchedule[1].equals("2") && dayCount >= 0) {
                    auxPayRoll(employees, i, calendary, day, month, paySchedule);
                }
            }
            i++;
        }
    }
}