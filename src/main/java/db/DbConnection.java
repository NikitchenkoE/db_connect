package db;

import constants.DbConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private Connection connection = null;
    private String url = DbConstants.DEFAULT_URL;
    private String user = DbConstants.DEFAULT_USER;
    private String password = DbConstants.DEFAULT_PASSWORD;

    public DbConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public DbConnection() {
    }

    public Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(url, user, password);
            }
            return connection;
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
