package com.ciandt.feedfront.employee;


import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
import com.ciandt.feedfront.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    public Employee employee1;
    public Employee employee2;
    @BeforeEach
    public void initEach() throws IOException, ComprimentoInvalidoException, EmailInvalidoException {
        employee1 = new Employee("Jose", "Silveira", "j.silveira@email.com");
        employee2 = null;

        Files.walk(Paths.get("src/main/resources/data/employee/"))
                .filter(p -> p.toString().endsWith(".byte"))
                .forEach(p -> {
                    new File(p.toString()).delete();
                });

        Employee.salvarEmployee(employee1);
    }

    @Test
    public void salvarEmployeeTest() throws ComprimentoInvalidoException, EmailInvalidoException, ArquivoException, EmployeeNaoEncontradoException {
        employee2 = new Employee("João", "Silveira", "j.silveira@email.com");

        Exception emailException = assertThrows(EmailInvalidoException.class, () -> {
            Employee.salvarEmployee(employee2);
        });

        String mensagemEsperada = "E-mail ja cadastrado no repositorio";
        String mensagemRecebida = emailException.getMessage();

        assertEquals(mensagemEsperada, mensagemRecebida);
    }

    @Test
    public void listarEmployees() throws ArquivoException, ComprimentoInvalidoException, EmailInvalidoException {
        List<Employee> employees = Employee.listarEmployees();

        assertFalse(employees.isEmpty());
        assertEquals(1, employees.size());
    }

    @Test
    public void buscarEmployee() throws ArquivoException, EmployeeNaoEncontradoException, EmailInvalidoException {
        Employee retornoDePesquisa = Employee.buscarEmployee(employee1.getId());

        assertEquals(retornoDePesquisa, employee1);

        Exception employeeNaoEncontradoException = assertThrows(EmployeeNaoEncontradoException.class, () -> {
            Employee.buscarEmployee(UUID.randomUUID().toString());
        });

        assertEquals(employeeNaoEncontradoException.getMessage(), "Employee não encontrado");
    }

    @Test
    public void atualizarEmployee() throws ComprimentoInvalidoException, EmployeeNaoEncontradoException, ArquivoException, EmailInvalidoException {
        String sobrenome = "Roberto";
        employee1.setSobrenome(sobrenome);

        String sobrenomeSalvo = Employee.buscarEmployee(employee1.getId()).getSobrenome();
        assertNotEquals(sobrenomeSalvo, sobrenome);

        Employee.atualizarEmployee(employee1);

        sobrenomeSalvo = Employee.buscarEmployee(employee1.getId()).getSobrenome();
        assertEquals(sobrenomeSalvo, sobrenome);
    }

    @Test
    public void apagarEmployee() throws ArquivoException, EmployeeNaoEncontradoException {
        String id = employee1.getId();
        Employee.apagarEmployee(id);

        Exception exception =  assertThrows(EmployeeNaoEncontradoException.class, () -> {
            Employee.buscarEmployee(id);
        });

        String mensagemRecebida = exception.getMessage();
        String mensagemEsperada = "Employee não encontrado";

        assertEquals(mensagemEsperada, mensagemRecebida);
    }

    @Test
    public void nomeDeveTerComprimentoMaiorQueDois() {
        Exception comprimentoInvalidoException = assertThrows(ComprimentoInvalidoException.class, () ->
                employee1 = new Employee("Ze", "Juvenil", "z.juvenil@ciandt.com")
        );

        String mensagemEsperada = "Comprimento do nome deve ser maior que 2 caracteres.";
        String mensagemRecebida = comprimentoInvalidoException.getMessage();
        assertEquals(mensagemEsperada, mensagemRecebida);
    }

    @Test
    public void sobrenomeDeveTerComprimentoMaiorQueDois() {
        Exception comprimentoInvalidoException = assertThrows(ComprimentoInvalidoException.class, () ->
                employee1 = new Employee("Joao", "ao", "z.juvenil@ciandt.com")
        );

        String mensagemEsperada = "Comprimento do sobrenome deve ser maior que 2 caracteres.";
        String mensagemRecebida = comprimentoInvalidoException.getMessage();
        assertEquals(mensagemEsperada, mensagemRecebida);

    }
}
