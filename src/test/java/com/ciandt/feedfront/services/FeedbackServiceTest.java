package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Feedback;
import com.ciandt.feedfront.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class FeedbackServiceTest {
    private Employee autor;
    private Employee proprietario;

    private Feedback feedback;

    private DAO<Feedback> feedbackDAO;
    private FeedbackService feedbackService;
    private Service<Employee> employeeService;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setup() throws BusinessException {
        feedbackDAO = (DAO<Feedback>) Mockito.mock(DAO.class);
        employeeService = (Service<Employee>) Mockito.mock(Service.class);

        feedbackService = new FeedbackService();
        feedbackService.setDAO(feedbackDAO);
        feedbackService.setEmployeeService(employeeService);

        autor = new Employee("João", "Silveira", "j.silveira@email.com");
        proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");

        feedback = new Feedback(LocalDate.now(), autor, proprietario, "descrição");

        autor.setId(1L);
        feedback.setId(1L);
        proprietario.setId(2L);

        when(employeeService.buscar(autor.getId())).thenReturn(autor);
        when(employeeService.buscar(autor.getId())).thenReturn(autor);

        feedbackService.salvar(feedback);
    }

    @Test
    public void listar() {
        when(feedbackDAO.listar()).thenReturn(List.of(feedback));

        List<Feedback> lista = assertDoesNotThrow(() -> feedbackService.listar());

        assertFalse(lista.isEmpty());
        assertTrue(lista.contains(feedback));
        assertEquals(1, lista.size());
    }

    @Test
    public void buscar() {
        long idInvalido = -1;
        long idValido = feedback.getId();

        when(feedbackDAO.buscar(idValido)).thenReturn(Optional.of(feedback));
        when(feedbackDAO.buscar(idInvalido)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> feedbackService.buscar(feedback.getId()));
        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> feedbackService.buscar(idInvalido));

        assertEquals("não foi possível encontrar o feedback", exception.getMessage());
    }

    @Test
    public void salvar() throws BusinessException {
        Employee employeeNaoSalvo = new Employee("miguel", "vitor", "m.vitor@email.com");
        employeeNaoSalvo.setId(-1L);

        Feedback feedbackValido1 = new Feedback(LocalDate.now(), autor, proprietario, "descrição");
        Feedback feedbackValido2 = new Feedback(LocalDate.now(), autor, proprietario, "descrição");

        Feedback feedbackInvalido1 = new Feedback(LocalDate.now(), null, null,"feedback sem autor e proprietario");
        Feedback feedbackInvalido2 = new Feedback(LocalDate.now(), null, employeeNaoSalvo,"feedback sem autor e proprietario");

        when(feedbackDAO.salvar(feedbackValido1)).thenReturn(feedbackValido1);
        when(feedbackDAO.salvar(feedbackValido2)).thenReturn(feedbackValido2);

        when(employeeService.buscar(employeeNaoSalvo.getId())).thenThrow(new EntidadeNaoEncontradaException("não foi possível encontrar o employee"));

        assertDoesNotThrow(() -> feedbackService.salvar(feedbackValido1));
        assertDoesNotThrow(() -> feedbackService.salvar(feedbackValido2));

        Exception exception1 = assertThrows(IllegalArgumentException.class,() -> feedbackService.salvar(feedbackInvalido1));
        Exception exception2 = assertThrows(IllegalArgumentException.class,() -> feedbackService.salvar(null));
        Exception exception3 = assertThrows(EntidadeNaoEncontradaException.class,() -> feedbackService.salvar(feedbackInvalido2));

        assertEquals("employee inválido", exception1.getMessage());
        assertEquals("feedback inválido", exception2.getMessage());
        assertEquals("não foi possível encontrar o employee", exception3.getMessage());
    }

}
