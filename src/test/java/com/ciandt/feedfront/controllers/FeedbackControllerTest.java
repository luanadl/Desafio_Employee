package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FeedbackControllerTest {

    private Feedback feedback;

    private Employee autor;

    private Employee proprietario;

    private FeedbackController controller;
    private Service<Feedback> feedbackService;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setup() throws IOException, BusinessException {
        feedbackService = (Service<Feedback>) Mockito.mock(Service.class);

        controller = new FeedbackController();
        controller.setService(feedbackService);

        autor = new Employee("João", "Silveira", "j.silveira@email.com");
        proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");

        feedback = new Feedback(LocalDate.now(), autor, proprietario,"Agradeco muito pelo apoio feito pelo colega!");//construtor 1
        feedback.setId(1L);

        when(feedbackService.salvar(feedback)).thenReturn(feedback);
        controller.salvar(feedback);
    }
    @Test
    public void listar() {
        Collection<Feedback> listaFeedback = controller.listar();

        assertNotNull(listaFeedback);
    }

    @Test
    public void buscar() throws BusinessException {
        long id = feedback.getId();

        when(feedbackService.buscar(id)).thenReturn(feedback);

        Feedback feedbackSalvo = assertDoesNotThrow(() -> controller.buscar(id));

        assertEquals(feedback, feedbackSalvo);

    }

    @Test
    public void salvar() throws BusinessException {
        Feedback novoFeedback = new Feedback(LocalDate.now(), null, proprietario, "novo");

        when(feedbackService.salvar(novoFeedback)).thenReturn(novoFeedback);

        Feedback feedbackSalvo = controller.salvar(novoFeedback);

        assertEquals(novoFeedback, feedbackSalvo);
    }

}
