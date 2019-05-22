package Source;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("=============== Seja bem-vindo ao Sistema Folha de Pagamento ===============");

        int ID = 0;
        boolean check = true;
        Scanner input = new Scanner(System.in);
        String[][] employees = new String[100][10];
        //Existência | Nome | Tipo | Salário | Comissão | Método | Sindicato | IDSindicato | TaxaSindicato | Endereço

        while(check)
        {
            switch(header(input))
            {
                case 1:
                    addEmployee(ID, input, employees);
                    break;
                case 2:
                    removeEmployee(ID, employees);
                    break;
                case 3:
                    break;
                case 4:
                    sellResult(input, employees);
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 0:
                    check = false;
                    break;
            }
        }
    }
    private static int header(Scanner input)
    {
        return Integer.parseInt(input.nextLine());
    }
    private static void addEmployee(int ID, Scanner input, String[][] employees)
    {
        employees[ID][0] = "true";
        System.out.print("Insira o nome do novo funcionário: ");
        employees[ID][1] = input.nextLine();
        System.out.println("\nTipos de funcionário.");
        System.out.println("[1] Horista\n[2] Assalariado\n[3] Comissionado.\n");
        System.out.print("Insira o tipo do novo funcionário: ");
        employees[ID][2] = input.nextLine();
        switch (Integer.parseInt(employees[ID][2]))
        {
            case 1:
                System.out.println("Insira o valor da hora de trabalho do novo funcionário: ");
                employees[ID][3] = input.nextLine();
                employees[ID][4] = "None";
                //employees[ID][5];
            case 2:
                System.out.println("Insira o valor do salário mensal do novo funcionário: ");
                employees[ID][3] = input.nextLine();
                employees[ID][4] = "None";
                //employees[ID][5];
            case 3:
                System.out.println("Insira o valor do salário mensal do novo funcionário: ");
                employees[ID][3] = input.nextLine();
                System.out.println("Insira o valor da comissão do novo funcionário em decimal (ex.: 0.2 = 20%): ");
                employees[ID][4] = input.nextLine();
                //employees[ID][5];
        }
        employees[ID][6] = "Não pertence ao Sindicato";
        employees[ID][7] = "None";
        employees[ID][8] = "None";
        System.out.print("Insira o endereço do novo funcionário: ");
        employees[ID][9]  = input.nextLine();
    }
    private static void removeEmployee(int ID, String[][] employees)
    {
        employees[ID][0] = "false";
    }
    private static void sellResult(Scanner input, String[][] employees)
    {
        int ID;
        double value, now, newValue;
        System.out.println("Insira o ID do funcionário que lançará o resultado da venda: ");
        ID = Integer.parseInt(input.nextLine());
        System.out.println("Insira o valor do resultado da venda: ");
        value = Double.parseDouble(input.nextLine());
        now = Double.parseDouble(employees[ID][3]);
        newValue = value*(Double.parseDouble(employees[ID][4])) + now;
        employees[ID][3] = Double.toString(newValue);
    }
}
