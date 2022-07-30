package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// A função do controller é tratar as solicitações.
// Ser capaz de levar o "pedido" ao "cozinheiro" e me trazer o "prato"
public class EmployeeControllerTest {
    private Employee employee;
    private EmployeeController employeeController;
    private Service<Employee> employeeService;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setup() throws BusinessException {
        employeeController = new EmployeeController();
        employeeService = (Service<Employee>) Mockito.mock(Service.class);
        employee = new Employee("João", "Silveira", "j.silveira@email.com");
        employee.setId(1L);

        employeeController.setService(employeeService);

        employeeController.salvar(employee);
    }

    @Test
    public void listar() {
        Collection<Employee> employees = assertDoesNotThrow(employeeController::listar);

        assertTrue(employees instanceof List);
    }

    @Test
    public void buscar() throws BusinessException {
        long id = employee.getId();
        when(employeeService.buscar(id)).thenReturn(employee);

        Employee employeeSalvo = assertDoesNotThrow(() -> employeeController.buscar(id));

        assertEquals(employee, employeeSalvo);
    }

    @Test
    public void salvar() throws BusinessException {
        Employee novoEmployee = new Employee("Cristiano", "Halland", "fifa@email.com");

        when(employeeService.salvar(novoEmployee)).thenReturn(novoEmployee);

        Employee employeeSalvo = assertDoesNotThrow(() -> employeeController.salvar(novoEmployee));

        assertEquals(novoEmployee, employeeSalvo);
    }

    @Test
    public void atualizar() throws BusinessException {
        long id = employee.getId();
        employee.setEmail("joao.silveira@email.com");

        when(employeeService.buscar(id)).thenReturn(employee);
        when(employeeService.atualizar(employee)).thenReturn(employee);

        Employee employeeAtualizado = assertDoesNotThrow(() -> employeeController.atualizar(employee));

        assertEquals(employee, employeeAtualizado);
    }

    @Test
    public void apagar() throws BusinessException {
        long id = employee.getId();
        when(employeeService.buscar(id)).thenReturn(employee);

        assertDoesNotThrow(() -> employeeController.apagar(id));
    }
}
