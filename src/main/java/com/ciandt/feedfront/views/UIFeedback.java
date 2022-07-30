package com.ciandt.feedfront.views;

import com.ciandt.feedfront.controllers.EmployeeController;
import com.ciandt.feedfront.controllers.FeedbackController;

import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class UIFeedback {
    private static FeedbackController feedbackController = new FeedbackController();

    public static void listarFeedback() {
        feedbackController = new FeedbackController();

        System.out.println("---------- Feedbacks ----------");
        try {
            List<Feedback> feedbacks = feedbackController.listar();
            System.out.println(feedbacks);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static Feedback consultarFeedback() {
        Feedback feedback = null;

        Scanner sc = new Scanner(System.in);
        long id;
        System.out.println("Entre com o ID do Feedback:");
        id = sc.nextLong();

        try {
            feedback = feedbackController.buscar(id);
            System.out.println(feedback.toString());
        } catch (Exception e) {
            System.err.println("Não foi possível encontrar o Feedback");
            e.printStackTrace();
        }

        return feedback;
    }

    public static void cadastrarFeedBack() {
        long idAutor, idProprietario;
        Employee autor = null, proprietario;
        String descricao, oqueMelhora, comoMelhora;
        LocalDate data = LocalDate.now();

        EmployeeController employeeController = new EmployeeController();

        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o ID do Employee autor (-1 se desejar manter anônimo):");
        idAutor = sc.nextLong();

        if (idAutor != -1) {
            try {
                autor = employeeController.buscar(idAutor);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                return;
            }
        }

        System.out.println("Digite o ID do Employee que receberá o Feedback:");
        idProprietario = sc.nextLong();

        try {
            proprietario = employeeController.buscar(idProprietario);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        System.out.println("Digite a descrição do Feedback:");
        descricao = sc.nextLine();
        System.out.println("Digite pontos em que você acha o Employee pode melhorar:");
        oqueMelhora = sc.nextLine();
        System.out.println("Digite sugestões do que Employee pode fazer para melhorar:");
        comoMelhora = sc.nextLine();

        try {
            Feedback feedback = new Feedback(data, autor, proprietario, descricao, oqueMelhora, comoMelhora);
            feedbackController.salvar(feedback);
        } catch (Exception e) {
            // ToDo: tratar as diferentes exceções
            System.err.println("Não foi possível cadastrar o Feedback");
            e.printStackTrace();
        }
    }
}
