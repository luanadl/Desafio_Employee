package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.controller.FeedbackController;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackControllerTest {

    private Feedback feedback;

    private Employee autor;

    private Employee proprietario;

    private FeedbackController controller;

    @BeforeEach
    public void initEach() throws IOException, ComprimentoInvalidoException {
        Files.walk(Paths.get("src/main/resources/data/feedback/"))
                .filter(p -> p.toString().endsWith(".byte"))
                .forEach(p -> {
                    new File(p.toString()).delete();
                });

        controller = new FeedbackController();
        autor = new Employee("João", "Silveira", "j.silveira@email.com");
        proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");

        feedback = new Feedback(LocalDate.now(), autor, proprietario,"Agradeco muito pelo apoio feito pelo colega!");//construtor 1

        try {
            controller.salvar(feedback);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void listar() {
        Collection<Feedback> listaFeedback = null;
        try {
            listaFeedback = controller.listar();
        } catch (ArquivoException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(listaFeedback);
    }

    @Test
    public void salvar() {
        assertDoesNotThrow(() -> controller.salvar(feedback));
    }

    @Test
    public void buscar() {
        String uuid = feedback.getId();

        Feedback feedbackSalvo = assertDoesNotThrow(() -> controller.buscar(uuid));

        assertEquals(feedback, feedbackSalvo);

    }

}
