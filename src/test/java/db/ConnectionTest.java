package db;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConnectionTest {


    DataSource defaultConnection = new DataSource();

    @Test
    void createConnectionDefault() throws SQLException {
        try(Connection connection = defaultConnection.getDataSource().getConnection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        }
    }

    @Test
    void createConnectionPostgre() throws SQLException {
        String url ="jdbc:postgresql:postgreDb";
        String user = "userPg";
        String password ="userPg";
        DataSource dataSource = new DataSource(url,user,password);

        try(Connection connection = dataSource.getDataSource().getConnection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        }
    }
}
