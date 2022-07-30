package com.ciandt.feedfront.views;

import java.io.IOException;

public class UIPrincipal {

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void menuInicial() {
        System.out.println("1. Menu Employee");
        System.out.println("2. Menu Feedback");
        System.out.println("3. Sair");
    }

    public static void menuDetalhado(int opcao) {
        int i = 1;

        if (opcao == 1) {
            System.out.println("---------- Menu Employee ----------");
        } else {
            System.out.println("---------- Menu Feedback ----------");
        }

        System.out.printf("%d. Listar registros%n", i++);
        System.out.printf("%d. Cadastrar%n", i++);
        System.out.printf("%d. Consultar%n", i++);

        if (opcao == 1) {
            System.out.printf("%d. Editar%n", i++);
            System.out.printf("%d. Excluir%n", i++);
        }

        System.out.printf("%d. Voltar ao menu anterior%n", i++);
    }

    public static boolean realizarAcao(int opcao, int opcaoDetalhe) {
        switch (opcaoDetalhe) {
            case 1:
                if (opcao == 1) {
                    System.out.println("---------- Employees ----------");
                    UIEmployee.listarEmployee();
                } else {
                    System.out.println("---------- Feedbacks ----------");
                    UIFeedback.listarFeedback();
                }

                System.out.println("---------- Ação concluida ----------");
                clearScreen();

                return true;
            case 2:
                if (opcao == 1) {
                    System.out.println("---------- Cadastrar Employee ----------");
                    UIEmployee.cadastrarEmployee();
                } else {
                    System.out.println("---------- Cadastrar Feedback ----------");
                    UIFeedback.cadastrarFeedBack();
                }

                System.out.println("---------- Ação concluida ----------");
                clearScreen();

                return true;
            case 3:
                if (opcao == 1) {
                    System.out.println("---------- Consultar Employee ----------");
                    UIEmployee.consultarEmployee();
                } else {
                    System.out.println("---------- Consultar Feedback ----------");
                    UIFeedback.consultarFeedback();
                }

                System.out.println("---------- Ação concluida ----------");
                clearScreen();
                return true;
            case 4:
                if (opcao == 1) {
                    System.out.println("---------- Editar Employee ----------");
                    UIEmployee.editarEmployee();
                } else {
                    return false;
                }
                System.out.println("---------- Ação concluida ----------");
                clearScreen();
                return true;
            case 5:
                if (opcao == 1) {
                    System.out.println("---------- Excluir Employee ----------");
                    UIEmployee.excluirEmployee();
                } else {
                    return true;
                }
                System.out.println("---------- Ação concluida ----------");
                clearScreen();
                return true;
            default:
                return false;
        }
    }
}
