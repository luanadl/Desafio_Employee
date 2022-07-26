package com.ciandt.feedfront.models;

import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;

import java.io.Serializable;
import java.util.UUID;

public class Employee implements Serializable {
    private String id;
    private String nome;
    private String sobrenome;
    private String email;
    public String arquivo; //TODO: alterar de acordo com a sua implementação
    private static final String repositorioPath = "src/main/resources/data/employee/"; //TODO: alterar de acordo com a sua implementação

    private static final long serialVersionID = 1;


    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        this.id = UUID.randomUUID().toString();
        this.arquivo = repositorioPath + getId() + ".byte";

        setNome(nome);
        setEmail(email);
        setSobrenome(sobrenome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ComprimentoInvalidoException {
        if (nome.length() < 3) {
            throw new ComprimentoInvalidoException("Comprimento do nome deve ser maior que 2 caracteres.");
        }

        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) throws ComprimentoInvalidoException {
        if (sobrenome.length() < 3) {
            throw new ComprimentoInvalidoException("Comprimento do sobrenome deve ser maior que 2 caracteres.");
        }

        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getArquivo() {
        return arquivo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Employee employee = (Employee) obj;

        return getId().equals(employee.getId());
    }

    @Override
    public int hashCode() {
        return 31 * 7 + ((id == null) ?  0 : id.hashCode());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
