package com.dlrproducoes.yourlibrary.model.tables;

public class Polo {
    private int id;
    private final String nome;
    private final String endereco;

    public Polo(int id, String nome, String endereco) {
        super();
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
    }

    public int getId() {
        return this.id;
    }
    public String getNome() {
        return this.nome;
    }

    public String getEndereco() {
        return this.endereco;
    }
}
