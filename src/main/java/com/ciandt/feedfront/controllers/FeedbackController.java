package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.models.Feedback;

import java.util.List;

public class FeedbackController {

    private Service<Feedback> service;

    public List<Feedback> listar() {
        throw new UnsupportedOperationException();
    }

    public Feedback buscar(long id) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    public Feedback salvar(Feedback feedback) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    public Feedback atualizar(Feedback feedback) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    public void apagar(String id) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    public void setService(Service<Feedback> service) {}

}
