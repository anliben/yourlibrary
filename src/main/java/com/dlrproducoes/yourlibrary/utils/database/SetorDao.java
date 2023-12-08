package com.dlrproducoes.yourlibrary.utils.database;

import com.dlrproducoes.yourlibrary.model.tables.Polo;
import com.dlrproducoes.yourlibrary.model.tables.Setor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SetorDao implements Dao<Setor>{
    @Override
    public List<Setor> consultAll() {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        Statement stmt = null;
        ResultSet result = null;

        List<Setor> setores = new ArrayList<Setor>();
        String sql = "SELECT * FROM setor";

        try {

            stmt = conexao.createStatement();
            result = stmt.executeQuery(sql);
            while(result.next()) {
                Setor setor = new Setor(result.getInt("id"), result.getString("polo"), result.getString("nome"), result.getString("area"));
                setores.add(setor);
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

        return setores;
    }

    public List<Setor> consultAllByName(String nome) {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        Statement stmt = null;
        ResultSet result = null;

        List<Setor> setores = new ArrayList<Setor>();
        String sql = String.format("SELECT * FROM setor WHERE polo = '%s'", nome);

        try {

            stmt = conexao.createStatement();
            result = stmt.executeQuery(sql);
            while(result.next()) {
                Setor setor = new Setor(result.getInt("id"), result.getString("polo"), result.getString("nome"), result.getString("area"));
                setores.add(setor);
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

        return setores;
    }

    @Override
    public Setor consult(Setor ob) {
        return null;
    }

    @Override
    public void save(Setor ob) {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO setor(nome, polo, area) VALUES(?,?,?)";

        try {

            pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, ob.getNome());
            pstmt.setString(2, ob.getPolo());
            pstmt.setString(3, ob.getArea());

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
    public void update(Setor ob) {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        PreparedStatement pstmt = null;

        String sql = "UPDATE setor SET nome = ?, polo = ?, area = ? WHERE id = ?";

        try {

            pstmt = conexao.prepareStatement(sql);

            pstmt.setString(1, ob.getNome());
            pstmt.setString(2, ob.getPolo());
            pstmt.setString(3, ob.getArea());
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
    public void delete(Setor ob) {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        PreparedStatement pstmt = null;

        String sql = "DELETE FROM setor WHERE id = ?";
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
