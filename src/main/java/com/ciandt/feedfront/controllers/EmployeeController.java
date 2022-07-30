package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.excecoes.BusinessException;

import java.util.List;

public class EmployeeController {
    private Service<Employee> service;

    public EmployeeController() {
        throw new UnsupportedOperationException();
    }

    public List<Employee> listar() {
        throw new UnsupportedOperationException();
    }

    public Employee buscar(long id) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    public Employee salvar(Employee employee) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    public Employee atualizar(Employee employee) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    public void apagar(long id) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    public void setService(Service<Employee> service) {}
}
