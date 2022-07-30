package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.excecoes.BusinessException;

import java.util.List;

public class EmployeeService implements Service<Employee> {
    private DAO<Employee> dao;

    public EmployeeService() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Employee> listar() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Employee buscar(long id) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Employee salvar(Employee employee) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Employee atualizar(Employee employee) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void apagar(long id) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDAO(DAO<Employee> dao) {
        throw new UnsupportedOperationException();
    }
}
