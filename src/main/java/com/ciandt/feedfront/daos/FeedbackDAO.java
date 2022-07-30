package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.models.Feedback;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class FeedbackDAO implements DAO<Feedback> {
    private EntityManager entityManager;

    @Override
    public List<Feedback> listar() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Feedback> buscar(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Feedback salvar(Feedback feedback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean apagar(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
    }
}
