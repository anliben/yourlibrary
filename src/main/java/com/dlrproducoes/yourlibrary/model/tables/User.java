package com.dlrproducoes.yourlibrary.model.tables;

public class User {
    private final String Nome;
    private final int Id;
    private final long Cpf;
    private final String Email;
    private final String Setor;

    public User(int id, String nome, long cpf, String email, String setor) {
        Id = id;
        Nome = nome;
        Cpf = cpf;
        Email = email;
        Setor = setor;
    }

    public int getId() {
        return Id;
    }

    public String getNome() {
        return Nome;
    }

    public long getCpf() {
        return Cpf;
    }

    public String getEmail() {
        return Email;
    }

    public String getSetor() {
        return Setor;
    }
}
