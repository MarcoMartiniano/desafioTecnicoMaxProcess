package com.marco.desafiotecnicomaxprocess;

import java.util.List;

public class Clientes {
    int id;
    String nome;
    String cpf;
    String dataCadastro;
    String dataNascimento;
    String uf;
    List<String> telefones;


    public Clientes() {
    }

    public Clientes(int id, String nome, String cpf, String dataCadastro, String dataNascimento, String uf, List<String> telefones) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataCadastro = dataCadastro;
        this.dataNascimento = dataNascimento;
        this.uf = uf;
        this.telefones = telefones;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }
}


