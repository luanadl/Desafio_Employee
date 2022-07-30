package com.ciandt.feedfront.models;

import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;

import javax.persistence.*;
import java.time.LocalDate;

public class Feedback {
    private Long id;
    private String descricao;
    private String oQueMelhora;
    private String comoMelhora;
    private LocalDate data;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Employee autor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Employee proprietario;

    public Feedback() {
    }

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao) throws ComprimentoInvalidoException {
        throw new UnsupportedOperationException();
    }

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao, String oQueMelhora, String comoMelhora) throws ComprimentoInvalidoException {
        throw new UnsupportedOperationException();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getoQueMelhora() {
        return oQueMelhora;
    }

    public void setoQueMelhora(String oQueMelhora) {
        this.oQueMelhora = oQueMelhora;
    }

    public String getComoMelhora() {
        return comoMelhora;
    }

    public void setComoMelhora(String comoMelhora) {
        this.comoMelhora = comoMelhora;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Employee getAutor() {
        return autor;
    }

    public void setAutor(Employee autor) {
        this.autor = autor;
    }

    public Employee getProprietario() {
        return proprietario;
    }

    public void setProprietario(Employee proprietario) {
        this.proprietario = proprietario;
    }

    // TODO: implementar toString
}
