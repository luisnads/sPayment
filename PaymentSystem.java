package Code;
import java.util.Scanner;

public class PaymentSystem {

    private static void showEmployees(String[][] employees) {
        int i = 0;
        while(employees[i][0] != null) {
            System.out.printf("%s ", employees[i][1]);
            i++;
        }
        System.out.println();
    }
    private static void showEmployeeInfo(String id, String[][] employees) {
        System.out.println();
        int index = getIndex(id, employees);
        for(int i = 0; i < 11; i++) {
            System.out.printf("Info %d | %s\n", i, employees[index][i]);
        }
        System.out.println();
    }
    private static String[][] getNewMatrix(int index, String[][] employees) {
        while(employees[index+1][0] != null) {
            employees[index] = employees[index + 1];
            index++;
        }
        employees[index][0] = null;
        return employees;
    }
    private static int getIndex(String id, String[][] employees) {
        int i = 0;
        while(employees[i][0] != null) {
            if(employees[i][0].equals(id))
                break;
            i++;
        }
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

    public static void main(String[] args) {
        System.out.println("=============== Seja bem-vindo ao Sistema Folha de Pagamento ===============\n");

        int id = 1, index = 0, iSchedule = 0;
        boolean check = true;
        Scanner input = new Scanner(System.in);
        String[][] employees = new String[100][12];
        String[] pSchedule = new String[20];
        //ID | Nome | Tipo | Salário | Comissão | Agenda de Pagamento | Sindicato | IDSindicato | TaxaSindicato | Endereço | TotalSalary | Método

        while(check) {
            switch(header(input)) {
                case 1:
                    addEmployee(index, id, input, employees);
                    id++;
                    index++;
                    break;
                case 2:
                    System.out.print("Insira o ID do funcionário que deseja remover: ");
                    //auxID = input.nextLine();
                    //int removedIndex = removeEmployee(auxID, employees);
                    //employees = getNewMatrix(removedIndex, employees);
                    //index--;
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
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    newSchedule(input, pSchedule, iSchedule);
                    iSchedule++;
                case 11:
                    showEmployeeInfo("1", employees);
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
        employees[index][10] = "0";
        System.out.print("Insira aqui o método de pagamento preferencial: ");
        employees[index][11] = input.nextLine();
        System.out.println();
    }
    /*private static int removeEmployee(String id, String[][] employees)
    {
        int i = 0;
        while(employees[i][0] != null) {
            if(employees[i][0].equals(id)) {
                employees[i][0] = null;
                break;
            }
            i++;
        }
        return i;
    }*/
    private static void sellResult(Scanner input, String[][] employees) {

        System.out.println("Insira o ID do funcionário que lançará o resultado da venda: ");
        String ID = input.nextLine();
        int index = getIndex(ID, employees);

        System.out.println("Insira o valor do resultado da venda: ");
        double value = Double.parseDouble(input.nextLine());

        employees[index][3] = Double.toString(value*(Double.parseDouble(employees[index][4])) + Double.parseDouble(employees[index][3]));
    }
    private static void hourlyCard(Scanner input, String[][] employees) {

        System.out.print("Insira o ID do funcionário que lançará o cartão de ponto: ");
        String ID = input.nextLine();
        int index = getIndex(ID, employees);

        System.out.print("Insira as informações do cartão de ponto (Quantidade de horas trabalhadas no dia): ");
        String hours = input.nextLine();

        employees[index][10] = Double.toString((Double.parseDouble(employees[index][10]) + Integer.parseInt(hours)*Double.parseDouble(employees[index][3])));
    }
    private static void serviceTax(Scanner input, String[][] employees) {

        System.out.print("Insira o ID do funcionário que terá a taxa de serviço: ");
        String ID = input.nextLine();
        int index = getIndex(ID, employees);

        System.out.print("Insira o valor da taxa de serviço: ");
        String tax = input.nextLine();

        employees[index][10] = Double.toString(Double.parseDouble(employees[index][10]) - Double.parseDouble(tax));
    }
    private static void setNewInfo(Scanner input, String[][] employees) {
        int confirm;

        System.out.print("Insira o ID do funcionário que terá as informações alteradas: ");
        String ID = input.nextLine();
        int index = getIndex(ID, employees);

        System.out.println("Deseja mudar o nome do funcionário? [0] Sim | [1] Não");
        confirm = Integer.parseInt(input.nextLine());
        if(confirm == 0) {
            System.out.print("Insira o novo nome do funcionário: ");
            employees[index][1] = input.nextLine();
        }

        System.out.println("Deseja mudar o tipo do funcionário? [0] Sim | [1] Não");
        confirm = Integer.parseInt(input.nextLine());
        if(confirm == 0) {
            getEmployeeType(input, index, employees);
        }

        System.out.println("Deseja mudar se o funcionário pertence ao sindicato? [0] Sim | [1] Não");
        confirm = Integer.parseInt(input.nextLine());
        if(confirm == 0) {
            System.out.print("O funcionário pertence ao sindicato? [S]im | [N]ão");
            if(input.nextLine().equals("S")) {
                employees[index][6] = "Sim";
                System.out.print("Insira o ID sindical do funcionário: ");
                employees[index][7] = input.nextLine();
                System.out.print("Insira a taxa sindical do funcionário em decimal (ex.: 0.2 = 20%: ");
                employees[index][8] = input.nextLine();
            }
            else {
                employees[index][6] = "Não";
                employees[index][7] = "None";
                employees[index][8] = "None";
            }
        }

        System.out.println("Deseja mudar o endereço do funcionário? [0] Sim | [1] Não");
        confirm = Integer.parseInt(input.nextLine());
        if(confirm == 0) {
            System.out.print("Insira o novo endereço do funcionário: ");
            employees[index][9] = input.nextLine();
        }
    }
    private static void newSchedule(Scanner input, String[] pSchedule, int iSchedule) {
        System.out.print("Insira a nova agenda de pagamento: ");
        pSchedule[iSchedule] = input.nextLine();
    }
}