package com.ciandt.feedfront.employee;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.util.List;
import java.util.UUID;

public class Employee {
    private String id;
    private String nome;
    private String sobrenome;
    private String email;
    private String arquivo = "arquivo.extensao"; //TODO: alterar de acordo com a sua implementação

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        this.id = UUID.randomUUID().toString();
    }

    public static Employee salvarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {
        return null;
    }

    public static Employee atualizarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException, EmployeeNaoEncontradoException {
        return null;
    }

    public static List<Employee> listarEmployees() throws ArquivoException {
        return null;
    }

    public static Employee buscarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        return null;
    }

    public static void apagarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
    }

    public String getNome() {
        return null;
    }

    public void setNome(String nome) throws ComprimentoInvalidoException {
    }

    public String getSobrenome() {
        return null;
    }

    public void setSobrenome(String sobrenome) throws ComprimentoInvalidoException {
    }

    public String getEmail() {
        return null;
    }

    public void setEmail(String email) {
    }

    public String getId() {
        return id;
    }

}

