package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.models.Feedback;
import com.ciandt.feedfront.models.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Mock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FeedbackServiceTest {

    private final LocalDate localDate = LocalDate.now();
    private final String LOREM_IPSUM_FEEDBACK = "Lorem Ipsum is simply dummy text of the printing and typesetting industry";
    private Feedback feedback;

    private Employee autor;

    private Employee proprietario;

    private Service<Feedback> service;

    private DAO<Feedback> feedbackDao;

    @BeforeEach
    public void initEach() throws IOException, BusinessException {
        // Este trecho de código serve somente para limpar o repositório
//        Files.walk(Paths.get("src/main/resources/data/feedback/"))
//                .filter(p -> p.toString().endsWith(".byte"))
//                .forEach(p -> {
//                    new File(p.toString()).delete();
//                });

        service = new FeedbackService();
        autor = new Employee("João", "Silveira", "j.silveira@email.com");
        proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");
        feedback = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);
        service.setDAO(feedbackDao);
        feedbackDao = (DAO<Feedback>) Mockito.mock(DAO.class);
    }

    @Test
    public void listar() throws IOException{
        List<Feedback> lista = assertDoesNotThrow(() -> service.listar());

        assertFalse(lista.isEmpty());
        assertTrue(lista.contains(feedback));
        assertEquals(1, lista.size());
    }

    @Test
    public void salvar() throws IOException, BusinessException, ComprimentoInvalidoException, EmployeeNaoEncontradoException, FeedbackNaoEncontradoException {
        Employee employeeNaoSalvo = new Employee("miguel", "vitor", "m.vitor@email.com");

        Employee proprietario2 = new Employee("Matiii", "Suuu", "m@");

        Feedback feedbackValido1 = new Feedback(localDate, proprietario2, proprietario, LOREM_IPSUM_FEEDBACK);
        Feedback feedbackValido2 = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);

        Feedback feedbackInvalido1 = new Feedback(localDate, null, null, "feedback sem autor e proprietario");
        Feedback feedbackInvalido2 = new Feedback(localDate, autor, employeeNaoSalvo, "feedback sem autor e proprietario");

        when(feedbackDao.salvar(feedbackValido1)).thenReturn(feedbackValido1);
        when(feedbackDao.salvar(feedbackValido2)).thenReturn(feedbackValido2);
        when(feedbackDao.buscar(feedbackValido2.getId())).thenReturn(feedbackValido2);
        when(feedbackDao.buscar(feedbackValido1.getId())).thenReturn(feedbackValido1);
        when(feedbackDao.buscar(feedbackInvalido2.getId())).thenThrow(new IOException());
        service.setDAO(feedbackDao);
        assertDoesNotThrow(() -> service.salvar(feedbackValido1));
        assertDoesNotThrow(() -> service.salvar(feedbackValido2));

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> service.salvar(feedbackInvalido1));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> service.salvar(null));
        Exception exception3 = assertThrows(EntidadeNaoEncontradaException.class, () -> service.salvar(feedbackInvalido2));

        assertEquals("feedback inválido", exception1.getMessage());
        assertEquals("feedback inválido", exception2.getMessage());
        assertEquals("não foi possível encontrar o feedback", exception3.getMessage());
    }

    @Test
    public void buscar() throws IOException, BusinessException, EmployeeNaoEncontradoException, FeedbackNaoEncontradoException {
        Feedback feedbackNaoSalvo = new Feedback(localDate, autor, proprietario, "tt");

        assertDoesNotThrow(() -> service.buscar(feedback.getId()));
        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> service.buscar(feedbackNaoSalvo.getId()));

        assertEquals("não foi possível encontrar o feedback", exception.getMessage());
    }

}
