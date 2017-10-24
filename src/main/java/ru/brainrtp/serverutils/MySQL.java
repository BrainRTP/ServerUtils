package ru.brainrtp.serverutils;

/**
 * Создано 11.10.17
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MySQL {
    private Connection connection;

    private String username;
    private String pass;
    private String database;
    private String url;


    public void setup(String username, String pass, String database, String url) {
        this.username = username;
        this.pass = pass;
        this.database = database;
        this.url = url;
    }

    public boolean testConnection(){
        try {
            openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        closeConnection();
        return true;
    }

    public ResultSet query(String request) {
        ResultSet result;
        try {
            openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        try {
            result = connection.createStatement().executeQuery(request);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
    public void update(String request) {
        try {
            connection.createStatement().executeUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url+database, username, pass);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
