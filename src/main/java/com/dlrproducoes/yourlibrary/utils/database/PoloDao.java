package com.dlrproducoes.yourlibrary.utils.database;

import com.dlrproducoes.yourlibrary.model.tables.Polo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PoloDao implements Dao<Polo> {
    @Override
    public List<Polo> consultAll() {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        Statement stmt = null;
        ResultSet result = null;

        List<Polo> polos = new ArrayList<Polo>();
        String sql = "SELECT * FROM polo";

        try {
            stmt = conexao.createStatement();
            result = stmt.executeQuery(sql);
            while(result.next()) {
                Polo polo = new Polo(result.getInt("id"), result.getString("nome"), result.getString("endereco"));
                polos.add(polo);
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

        return polos;
    }

    @Override
    public Polo consult(Polo ob) {
       return null;
    }

    @Override
    public void save(Polo ob) {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO polo(nome, endereco) VALUES(?,?)";

        try {
            pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, ob.getNome());
            pstmt.setString(2, ob.getEndereco());
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
    public void update(Polo ob) {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        PreparedStatement pstmt = null;

        String sql = "UPDATE polo SET nome = ?, endereco = ? WHERE id = ?";

        try {
            pstmt = conexao.prepareStatement(sql);

            pstmt.setString(1, ob.getNome());
            pstmt.setString(2, ob.getEndereco());
            pstmt.setInt(3, ob.getId());
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
    public void delete(Polo ob) {

        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        PreparedStatement pstmt = null;

        String sql = "DELETE FROM polo WHERE id = ?";
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
