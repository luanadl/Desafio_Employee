package com.ciandt.feedfront.controller;

import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.models.Employee;

import java.util.List;

public class EmployeeController {
    private Service<Employee> service;

    public EmployeeController() {
    }

    public List<Employee> listar() throws ArquivoException {
        return service.listar();
    }

    public Employee buscar(String id) throws BusinessException, ArquivoException {
        return service.buscar(id);
    }

    public Employee salvar(Employee employee) throws BusinessException, ArquivoException {
        return service.salvar(employee);
    }

    public Employee atualizar(Employee employee) throws BusinessException, ArquivoException {
        return service.atualizar(employee);
    }

    public void apagar(String id) throws BusinessException, ArquivoException {
        service.apagar(id);
    }

    public void setService(Service<Employee> service) {
        this.service = service;
    }
}
