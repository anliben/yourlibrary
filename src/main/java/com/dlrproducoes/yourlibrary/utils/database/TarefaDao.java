package com.dlrproducoes.yourlibrary.utils.database;

import com.dlrproducoes.yourlibrary.model.tables.Tarefa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDao implements Dao<Tarefa> {
    @Override
    public List<Tarefa> consultAll() {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        Statement stmt = null;
        ResultSet result = null;

        List<Tarefa> tarefas = new ArrayList<Tarefa>();
        String sql = "SELECT * FROM tarefa";

        try {

            stmt = conexao.createStatement();
            result = stmt.executeQuery(sql);
            while(result.next()) {
                Tarefa tarefa = new Tarefa(result.getInt("id"), result.getInt("id_user"), result.getString("nome"), result.getString("descricao"));
                tarefas.add(tarefa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexao != null)  conexao.close();
                if (stmt != null) stmt.close();
                if (result != null) result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return tarefas;
    }

    public List<Tarefa> consultAllByID(int id) {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        Statement stmt = null;
        ResultSet result = null;

        List<Tarefa> tarefas = new ArrayList<Tarefa>();
        String sql = "SELECT * FROM tarefa WHERE id_user = "+id;

        try {

            stmt = conexao.createStatement();
            result = stmt.executeQuery(sql);
            while(result.next()) {
                Tarefa tarefa = new Tarefa(result.getInt("id"), result.getInt("id_user"), result.getString("nome"), result.getString("descricao"));
                tarefas.add(tarefa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexao != null)  conexao.close();
                if (stmt != null) stmt.close();
                if (result != null) result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return tarefas;
    }

    @Override
    public Tarefa consult(Tarefa ob) {
       return null;
    }

    @Override
    public void save(Tarefa ob) {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO tarefa(id_user, nome, descricao) VALUES(?,?,?)";

        try {

            pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, ob.getUserID());
            pstmt.setString(2, ob.getNome());
            pstmt.setString(3, ob.getDescricao());
            pstmt.executeUpdate();
            System.out.println("Dados incluidos com sucesso");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexao != null)  conexao.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Tarefa ob) {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        PreparedStatement pstmt = null;

        String sql = "UPDATE tarefa SET id_user = ?, nome = ?, descricao = ? WHERE id = ?";

        try {

            pstmt = conexao.prepareStatement(sql);

            pstmt.setInt(1, ob.getUserID());
            pstmt.setString(2, ob.getNome());
            pstmt.setString(3, ob.getDescricao());
            pstmt.setInt(4, ob.getId());
            pstmt.executeUpdate();

            System.out.println("Dados updated com sucesso");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexao != null)  conexao.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Tarefa ob) {

        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        PreparedStatement pstmt = null;

        String sql = "DELETE FROM tarefa WHERE id = ?";
        try {

            pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, ob.getId());
            pstmt.executeUpdate();

            System.out.println("Deletado com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conexao != null)  conexao.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
