package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// O DAO é resposável pela persistência dos dados
// Com esse mecanismo de persistência (arquivos), não possui responsabilidades de validar regras
public class FeedbackDAOTest {
    private Employee autor;
    private Employee proprietario;
    private Feedback feedback;

    private DAO<Feedback> feedbackDAO;
    private EntityManager entityManager;

    @BeforeEach
    public void setup() throws ComprimentoInvalidoException {
        EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("feedfront");
        entityManager = entityManagerFactory.createEntityManager();

        feedbackDAO = new FeedbackDAO();
        feedbackDAO.setEntityManager(entityManager);

        autor = new Employee("joao", "silveira", "j.silveira@email.com");
        proprietario = new Employee("joao", "bruno", "j.bruno@email.com");

        feedback = new Feedback(LocalDate.now(), autor, proprietario, "descrição");

        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Feedback f where 1 = 1").executeUpdate();
        entityManager.createQuery("delete from Employee e where 1 = 1").executeUpdate();

        entityManager.persist(autor);
        entityManager.persist(proprietario);
        entityManager.getTransaction().commit();

        feedbackDAO.salvar(feedback);
    }

    @AfterEach
    public void closeEntityManager() {
        entityManager.close();
    }

    @Test
    public void listar() {
        List<Feedback> result = feedbackDAO.listar();

        assertFalse(result.isEmpty());
    }

    @Test
    public void buscar() {
        long idInvalido = -1;
        long idValido = feedback.getId();

        Optional<Feedback> vazio = feedbackDAO.buscar(idInvalido);
        Optional<Feedback> preenchido = feedbackDAO.buscar(idValido);

        assertTrue(vazio.isEmpty());
        assertTrue(preenchido.isPresent());
        assertEquals(preenchido.get(), feedback);
    }

    @Test
    public void salvar() throws ComprimentoInvalidoException {
        Feedback feedbackValido1 = new Feedback(LocalDate.now(), null, proprietario, "descrição");
        Feedback feedbackValido2 = new Feedback(LocalDate.now(), null, proprietario, "descrição", "oque", "como");

        Feedback feedbackInvalido = new Feedback(LocalDate.now(), null, null, "descrição");

        assertDoesNotThrow(() -> feedbackDAO.salvar(feedbackValido1));
        assertDoesNotThrow(() -> feedbackDAO.salvar(feedbackValido2));

        PersistenceException exception = assertThrows(PersistenceException.class, () -> feedbackDAO.salvar(feedbackInvalido));

        assertTrue(exception.getCause() instanceof PropertyValueException);
    }
}
