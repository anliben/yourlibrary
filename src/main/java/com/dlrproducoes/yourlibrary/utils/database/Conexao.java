package com.dlrproducoes.yourlibrary.utils.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class Conexao {
    private static Conexao instance;

    public static synchronized Conexao getInstance() {
        // Se a instância ainda não foi criada, cria-a
        if (instance == null) {
            instance = new Conexao();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection =  null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://viaduct.proxy.rlwy.net:18263/railway", "root", "6fFfc-6-Ad--HHe-ChhDg4-hghB-5gf4");
            System.out.println("Conexao estabelecida");
        } catch (SQLException e) {
            System.out.println("falha ao estabelecer uma conexao");
            e.printStackTrace();
        }


        return connection;
    }
}
