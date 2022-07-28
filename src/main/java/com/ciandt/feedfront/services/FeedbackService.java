package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.models.Feedback;

import java.io.IOException;
import java.util.List;

public class FeedbackService implements Service<Feedback> {
    private DAO<Feedback> dao;
    private EmployeeService employeeService;
    public FeedbackService(){

    }

    @Override
    public List<Feedback> listar() throws ArquivoException {
        try {
            return dao.listar();
        } catch (IOException e) {
            throw new ArquivoException("");
        }
    }

    @Override
    public Feedback buscar(String id) throws ArquivoException, BusinessException {

        try {
            return dao.buscar(id);
        } catch (IOException | FeedbackNaoEncontradoException | EmployeeNaoEncontradoException e) {
            throw new BusinessException("Feedback não encontrado");
        }
    }

    @Override
    public Feedback salvar(Feedback feedback) throws ArquivoException, BusinessException {
        System.out.println(feedback);
        if (feedback == null)
            throw new IllegalArgumentException("feedback null");
        if (feedback.getProprietario() == null)
            throw new IllegalArgumentException("prop inválido");
        if (feedback.getAutor() == null)
            throw new IllegalArgumentException("autor inválido");

        buscar(feedback.getId());
        try {
            dao.salvar(feedback);
        } catch (IOException e) {
            throw new ArquivoException("");
        }
        return feedback;


//        try {
//            return dao.salvar(feedback);
//        } catch (IOException e) {
//            throw new BusinessException("");
//        }
    }

    @Override
    public Feedback atualizar(Feedback feedback) throws ArquivoException, BusinessException {
        buscar(feedback.getId());
        try {
            return dao.salvar(feedback);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apagar(String id) throws ArquivoException, BusinessException {
        try {
            dao.apagar(id);
        } catch (IOException e) {
            throw new EntidadeNaoEncontradaException("Feedback não encontrado");
        } catch (EmployeeNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setDAO(DAO<Feedback> dao) {
        this.dao = dao;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
