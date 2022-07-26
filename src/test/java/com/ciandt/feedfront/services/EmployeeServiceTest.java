package com.ciandt.feedfront.services;


import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// O Service deve ser capaz de trabalhar em conjunto com o DAO para executar as tarefas
// Sempre valendo-se das regras de negócio
public class EmployeeServiceTest {
    private Employee employee;
    private DAO<Employee> employeeDAO;
    private Service<Employee> employeeService;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void initEach() throws IOException, BusinessException {
        employeeService = new EmployeeService();
        employeeDAO = (DAO<Employee>) Mockito.mock(DAO.class);
        employee = new Employee("João", "Silveira", "j.silveira@email.com");

        employeeService.setDAO(employeeDAO);

        employeeService.salvar(employee);
    }

    @Test
    public void listar() throws IOException {
        when(employeeDAO.listar()).thenReturn(List.of(employee));

        List<Employee> employees = employeeService.listar();

        assertFalse(employees.isEmpty());
        assertTrue(employees.contains(employee));
        assertEquals(1, employees.size());
    }

    // Nota: esses dois métodos estão testando o "buscar" do service
    // Mas estão separados para reforçar a independência dos testes como manda o padrão FIRST: https://hackernoon.com/test-f-i-r-s-t-65e42f3adc17
    @Test
    public void buscarMalSucedida() throws IOException {
        String uuid = "11f2105a-4f5b-4a48-bf57-3a4ff8b477b1";

        when(employeeDAO.buscar(uuid)).thenThrow(FileNotFoundException.class);

        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> employeeService.buscar(uuid));
        assertEquals("não foi possível encontrar o employee", exception.getMessage());
    }

    @Test
    public void buscarBemSucedida() throws IOException {
        String uuid = employee.getId();

        when(employeeDAO.buscar(uuid)).thenReturn(employee);

        Employee employeeSalvo = assertDoesNotThrow(() -> employeeService.buscar(uuid));

        assertEquals(employeeSalvo, employee);
    }

    @Test
    public void salvar() throws IOException, ComprimentoInvalidoException {
        Employee employeeValido = new Employee("João", "Silveira", "joao.silveira@email.com");
        Employee employeeInvalido = new Employee("José", "Silveira", "j.silveira@email.com");

        when(employeeDAO.salvar(employeeValido)).thenReturn(employeeValido);
        when(employeeDAO.listar()).thenReturn(List.of(employee, employeeValido));

        assertDoesNotThrow(() -> employeeService.salvar(employeeValido));
        Exception exception1 = assertThrows(EmailInvalidoException.class, () -> employeeService.salvar(employeeInvalido));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> employeeService.salvar(null));

        assertEquals("já existe um employee cadastrado com esse e-mail", exception1.getMessage());
        assertEquals("employee inválido", exception2.getMessage());
    }

    @Test
    public void atualizar() throws IOException, ComprimentoInvalidoException, BusinessException, ArquivoException {
        Employee employee2 = new Employee("Bruno", "Silveira", "b.silveira@email.com");
        Employee employee3 = new Employee("Vitor", "Fernandes", "vf.silveira@email.com");

        when(employeeDAO.salvar(employee)).thenReturn(employee);
        when(employeeDAO.salvar(employee2)).thenReturn(employee2);

        when(employeeDAO.buscar(employee3.getId())).thenThrow(FileNotFoundException.class);

        employeeService.salvar(employee);

        when(employeeDAO.listar()).thenReturn(List.of(employee));

        employeeService.salvar(employee2);

        employee.setEmail("joao.silveira@email.com");
        employee2.setNome("Jean");
        employee2.setEmail("joao.silveira@email.com");

        Employee employeeSalvo = assertDoesNotThrow(() -> employeeService.atualizar(employee));

        assertEquals(employee, employeeSalvo);
        assertThrows(EntidadeNaoEncontradaException.class, () -> employeeService.atualizar(employee3));
        Exception exception = assertThrows(EmailInvalidoException.class, () -> employeeService.atualizar(employee2));

        assertEquals("E-mail ja cadastrado no repositorio", exception.getMessage());
    }

    @Test
    public void apagar() throws IOException, ComprimentoInvalidoException {
        Employee employee2 = new Employee("Bruno", "Silveira", "b.silveira@email.com");
        String uuidValido = employee.getId();
        String uuidInvalido = employee2.getId();

        when(employeeDAO.apagar(uuidValido)).thenReturn(true);
        when(employeeDAO.apagar(uuidInvalido)).thenThrow(FileNotFoundException.class);

        assertThrows(EntidadeNaoEncontradaException.class, () -> employeeService.apagar(uuidInvalido));

        assertDoesNotThrow(() -> employeeService.apagar(uuidValido));

    }
}
