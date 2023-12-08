package com.dlrproducoes.yourlibrary.model.tables;

public class Tarefa {
    private final int Id;
    private final int IdUser;
    private final String Nome;
    private final String Descricao;

    public Tarefa(int id, int idUser, String Nome, String Descricao) {
        this.Id = id;
        this.IdUser = idUser;
        this.Nome = Nome;
        this.Descricao = Descricao;
    }

    public int getId() {
        return Id;
    }

    public int getUserID() {
        return IdUser;
    }

    public String getNome() {
        return Nome;
    }

    public String getDescricao() {
        return Descricao;
    }
}
