package com.dlrproducoes.yourlibrary.model.tables;

public class Setor {
    private final String Polo;
    private final int Id;
    private final String Nome;
    private final String Area;

    public Setor(int id, String polo, String nome, String area) {
        Id = id;
        Polo = polo;
        Nome = nome;
        Area = area;
    }

    public String getPolo() {
        return Polo;
    }

    public String getArea() {
        return Area;
    }

    public String getNome() { return Nome; }

    public int getId() {
        return Id;
    }
}
