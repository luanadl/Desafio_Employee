package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.models.Employee;

import java.io.IOException;
import java.util.List;

public class EmployeeService implements Service<Employee> {
    private DAO<Employee> dao;

    public EmployeeService() {
    }

    @Override
    public List<Employee> listar() throws ArquivoException {
        try {
            return dao.listar();
        } catch (IOException e) {
            throw new ArquivoException("");
        }
    }

    @Override
    public Employee buscar(String id) throws ArquivoException, BusinessException {
        try {
            return dao.buscar(id);
        } catch (IOException | EmployeeNaoEncontradoException e) {
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o employee");
        } catch (FeedbackNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee salvar(Employee employee) throws ArquivoException, BusinessException {
        if (employee == null){
            throw new IllegalArgumentException("employee inválido");
        }

        boolean emailExistente = false;
        List<Employee> employees = listar();
        for (Employee employeeSalvo: employees) {
            if (!employeeSalvo.getId().equals(employee.getId()) && employeeSalvo.getEmail().equals(employee.getEmail())) {
                emailExistente = true;
                break;
            }
        }
        if (emailExistente) {
            throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
        }
        try {
            return dao.salvar(employee);
        } catch (IOException e) {
            throw new BusinessException("");
        }
    }

    @Override
    public Employee atualizar(Employee employee) throws ArquivoException, BusinessException {
        buscar(employee.getId());

        boolean emailExistente = false;
        List<Employee> employees = listar();
        for (Employee employeeSalvo: employees) {
            if (!employeeSalvo.getId().equals(employee.getId()) && employeeSalvo.getEmail().equals(employee.getEmail())) {
                emailExistente = true;
                break;
            }
        }
        if (emailExistente) {
            throw new EmailInvalidoException("E-mail ja cadastrado no repositorio");
        }

        try {
            return dao.salvar(employee);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apagar(String id) throws ArquivoException, BusinessException {
        try {
            dao.apagar(id);
        } catch (IOException | EmployeeNaoEncontradoException e) {
            throw new EntidadeNaoEncontradaException("Employee não encontrado");
        }
    }

    @Override
    public void setDAO(DAO<Employee> dao) {
        this.dao = dao;
    }
}
