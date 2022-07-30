package com.ciandt.feedfront.services;


import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.models.Employee;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.PersistenceException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

// O Service deve ser capaz de trabalhar em conjunto com o DAO para executar as tarefas
// Sempre valendo-se das regras de negócio
public class EmployeeServiceTest {
    private Employee employee;

    private DAO<Employee> employeeDAO;
    private Service<Employee> employeeService;

    // essa exception é apenas um Mock...
    private ConstraintViolationException constraintViolationException = new ConstraintViolationException("string", new SQLException(), "string");

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setup() throws BusinessException {
        employeeService = new EmployeeService();
        employeeDAO = (DAO<Employee>) Mockito.mock(DAO.class);

        employee = new Employee("João", "Silveira", "j.silveira@email.com");
        employee.setId(1L);

        employeeService.setDAO(employeeDAO);

        employeeService.salvar(employee);
    }

    @Test
    public void listar() {
        when(employeeDAO.listar()).thenReturn(List.of(employee));

        List<Employee> employees = employeeService.listar();

        assertFalse(employees.isEmpty());
        assertTrue(employees.contains(employee));
        assertEquals(1, employees.size());
    }

    // Nota: esses dois métodos estão testando o "buscar" do service
    // Mas estão separados para reforçar a independência dos testes como manda o padrão FIRST: https://hackernoon.com/test-f-i-r-s-t-65e42f3adc17
    @Test
    public void buscarMalSucedida() {
        long id = -1;

        when(employeeDAO.buscar(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> employeeService.buscar(id));
        assertEquals("não foi possível encontrar o employee", exception.getMessage());
    }

    @Test
    public void buscarBemSucedida() {
        long id = 1;

        when(employeeDAO.buscar(id)).thenReturn(Optional.of(employee));

        Employee employeeSalvo = assertDoesNotThrow(() -> employeeService.buscar(id));

        assertEquals(employeeSalvo, employee);
    }

    @Test
    public void salvar() throws ComprimentoInvalidoException {
        Employee employeeValido = new Employee("João", "Silveira", "joao.silveira@email.com");
        Employee employeeInvalido = new Employee("José", "Silveira", "j.silveira@email.com");

        when(employeeDAO.salvar(employeeValido)).thenReturn(employeeValido);
        when(employeeDAO.salvar(employeeInvalido)).thenThrow(new PersistenceException(constraintViolationException));

        assertDoesNotThrow(() -> employeeService.salvar(employeeValido));

        Exception exception1 = assertThrows(EmailInvalidoException.class, () -> employeeService.salvar(employeeInvalido));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> employeeService.salvar(null));

        assertEquals("já existe um employee cadastrado com esse e-mail", exception1.getMessage());
        assertEquals("employee inválido", exception2.getMessage());
    }

    @Test
    public void atualizacaoBemSucedida() {
        employee.setEmail("joao.silveira@email.com");

        when(employeeDAO.salvar(employee)).thenReturn(employee);
        when(employeeDAO.buscar(employee.getId())).thenReturn(Optional.of(employee));

        Employee employeeSalvo = assertDoesNotThrow(() -> employeeService.atualizar(employee));

        assertEquals(employee, employeeSalvo);
    }

    @Test
    public void atualizacaoMalSucedida() throws BusinessException {
        Employee employee2 = new Employee("Bruno", "Silveira", "b.silveira@email.com");
        Employee employee3 = new Employee("Maria", "Silveira", "b.silveira@email.com");

        employee2.setId(10L);

        when(employeeDAO.salvar(employee)).thenReturn(employee);
        when(employeeDAO.salvar(employee2)).thenReturn(employee2);
        when(employeeDAO.buscar(employee2.getId())).thenReturn(Optional.of(employee2));

        employeeService.salvar(employee);
        employeeService.salvar(employee2);

        employee.setEmail("j.silveira@email.com");

        when(employeeDAO.salvar(employee2)).thenThrow(new PersistenceException(constraintViolationException));

        employee2.setNome("Jean");
        employee2.setEmail("joao.silveira@email.com");

        assertThrows(IllegalArgumentException.class, () -> employeeService.atualizar(null));
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> employeeService.atualizar(employee3));
        Exception exception2 = assertThrows(EmailInvalidoException.class, () -> employeeService.atualizar(employee2));

        assertEquals("employee inválido: não possui ID", exception1.getMessage());
        assertEquals("já existe um employee cadastrado com esse e-mail", exception2.getMessage());
    }

    @Test
    public void apagar() {
        long idInvalido = -1;
        long idValido = employee.getId();

        when(employeeDAO.buscar(idInvalido)).thenReturn(Optional.empty());
        when(employeeDAO.buscar(employee.getId())).thenReturn(Optional.of(employee));

        assertDoesNotThrow(() -> employeeService.apagar(idValido));

        assertThrows(EntidadeNaoEncontradaException.class, () -> employeeService.apagar(idInvalido));
    }
}
