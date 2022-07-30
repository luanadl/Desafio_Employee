package com.ciandt.feedfront.views;

import com.ciandt.feedfront.controllers.EmployeeController;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.models.Employee;

import java.util.List;
import java.util.Scanner;

public class UIEmployee {
    private static EmployeeController employeeController = new EmployeeController();

    public static void listarEmployee() {
        try {
            List<Employee> employees = employeeController.listar();
            System.out.println(employees);
        } catch (Exception e) {
            System.err.println("Erro ao listar employees");
            e.printStackTrace();
        }
    }

    public static Employee consultarEmployee() {
        long id;
        Scanner sc = new Scanner(System.in);

        System.out.println("Entre com o id:");
        id = sc.nextLong();
        Employee employee = null;

        try {
            employee = employeeController.buscar(id);
            System.out.println(employee);
        } catch (BusinessException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return employee;
    }

    public static void cadastrarEmployee() {
        String nome, sobrenome, email;
        Scanner sc = new Scanner(System.in);

        System.out.println("Entre com o nome:");
        nome = sc.nextLine();

        System.out.println("Entre com o sobrenome:");
        sobrenome = sc.nextLine();

        System.out.println("Entre com o email:");
        email = sc.nextLine();

        EmployeeController employeeController = new EmployeeController();
        Employee employee;

        try {
            employee = employeeController.salvar(new Employee(nome, sobrenome, email));
            System.out.println("Employee salvo: " + employee.getId());
        } catch (BusinessException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void editarEmployee() {
        String nome, sobrenome, email;
        Scanner sc = new Scanner(System.in);

        int opcao = 0;

        Employee employee = consultarEmployee();

        if (employee == null) return;

        menuCampoEdicao();
        opcao = Integer.parseInt(sc.nextLine());

        while (opcao != 4) {

            try {
                switch (opcao) {
                    case 1:
                        System.out.println("Digite o novo nome:");
                        nome = sc.nextLine();
                        employee.setNome(nome);
                        System.out.println("Campo atualizado.");
                        break;
                    case 2:
                        System.out.println("Digite o novo sobrenome:");
                        sobrenome = sc.nextLine();
                        employee.setSobrenome(sobrenome);
                        System.out.println("Campo atualizado.");
                        break;
                    case 3:
                        System.out.println("Digite o novo e-mail:");
                        email = sc.nextLine();
                        employee.setEmail(email);
                        System.out.println("Campo atualizado.");
                        break;
                    default:
                        System.out.println("Opção inválida. Digite novamente");
                        opcao = sc.nextInt();
                }

                employeeController.atualizar(employee);
            } catch (BusinessException e) {
                System.err.println("O dado informado é inválido");
                e.printStackTrace();
            }

            if (opcao >0 && opcao <4) {
                menuCampoEdicao();
                opcao = sc.nextInt();
            }

        }

    }

    public static void excluirEmployee() {
        Scanner sc = new Scanner(System.in);

        Employee employee = consultarEmployee();

        if (employee == null) return;

        System.out.println("Tem certeza que deseja excluir esse registro?");
        System.out.println("1. Confirmar");
        System.out.println("2. Cancelar");


        int opcao = sc.nextInt();

        while (opcao != 2) {
            if (opcao == 1) {
                try {
                    employeeController.apagar(employee.getId());
                } catch (Exception e){
                    // ToDo: tratar as diferentes exceções (principalmente relacionadas a Foreign Key)
                }
                opcao = 2;
            } else {
                System.out.println("Opção inválida. Digite novamente");
                opcao = sc.nextInt();
            }
        }

    }

    public static void menuCampoEdicao(){
        System.out.println("Selecione o campo que deseja editar:");
        System.out.println("1. Nome");
        System.out.println("2. Sobrenome");
        System.out.println("3. E-mail");
        System.out.println("4. Finalizar edição");
    }
}