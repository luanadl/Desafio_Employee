package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.models.Employee;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class EmployeeDAO implements DAO<Employee> {
    private EntityManager entityManager;

    public EmployeeDAO() {
    }

    @Override
    public List<Employee> listar() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Employee> buscar(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Employee salvar(Employee employee) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean apagar(long id)  {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
    }
}
