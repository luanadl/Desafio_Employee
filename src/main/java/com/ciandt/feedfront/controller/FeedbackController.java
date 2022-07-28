package com.ciandt.feedfront.controller;

import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.models.Feedback;

import java.util.List;

public class FeedbackController {
    private Service<Feedback> service;

    public FeedbackController() {
    }

    public List<Feedback> listar() throws ArquivoException {
        return service.listar();
    }

    public Feedback buscar(String id) throws BusinessException, ArquivoException {
        return service.buscar(id);
    }

    public Feedback salvar(Feedback feedback) throws BusinessException, ArquivoException {
        return service.salvar(feedback);
    }

    public Feedback atualizar(Feedback feedback) throws BusinessException, ArquivoException {
        return service.atualizar(feedback);
    }

    public void apagar(String id) throws BusinessException, ArquivoException {
        service.apagar(id);
    }

    public void setService(Service<Feedback> service) {
        this.service = service;
    }
}
