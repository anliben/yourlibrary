package com.dlrproducoes.yourlibrary.utils.database;

import com.dlrproducoes.yourlibrary.model.tables.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {
    @Override
    public List<User> consultAll() {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        Statement stmt = null;
        ResultSet result = null;

        List<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM usuario";

        try {

            stmt = conexao.createStatement();
            result = stmt.executeQuery(sql);
            while(result.next()) {
                User user = new User(
                    result.getInt("id"),
                    result.getString("nome"),
                    result.getLong("cpf"),
                    result.getString("email"),
                    result.getString("setor")
                    );
                users.add(user);
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

        return users;
    }

    public List<User> consultBySetor(String setor) {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        Statement stmt = null;
        ResultSet result = null;

        List<User> users = new ArrayList<User>();
        String sql = String.format("SELECT * FROM usuario WHERE setor = '%s'", setor);

        try {

            stmt = conexao.createStatement();
            result = stmt.executeQuery(sql);
            while(result.next()) {
                User user = new User(
                        result.getInt("id"),
                        result.getString("nome"),
                        result.getLong("cpf"),
                        result.getString("email"),
                        result.getString("setor")
                );
                users.add(user);
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

        return users;
    }

    @Override
    public User consult(User ob) {
       return null;
    }

    @Override
    public void save(User ob) {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO usuario(nome, cpf, email, setor) VALUES(?,?,?,?)";

        try {

            pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, ob.getNome());
            pstmt.setLong(2, ob.getCpf());
            pstmt.setString(3, ob.getEmail());
            pstmt.setString(4, ob.getSetor());

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
    public void update(User ob) {
        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        PreparedStatement pstmt = null;

        String sql = "UPDATE usuario SET nome = ?, cpf = ?, email = ?, setor = ? WHERE id = ?";

        try {

            pstmt = conexao.prepareStatement(sql);

            pstmt.setString(1, ob.getNome());
            pstmt.setLong(2, ob.getCpf());
            pstmt.setString(3, ob.getEmail());
            pstmt.setString(4, ob.getSetor());
            pstmt.setInt(5, ob.getId());

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
    public void delete(User ob) {

        Conexao conn = Conexao.getInstance();
        Connection conexao = conn.getConnection();
        PreparedStatement pstmt = null;

        String sql = "DELETE FROM usuario WHERE id = ?";
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
