package db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryHandlerTest {

    String url = "jdbc:postgresql:postgreDb";
    String user = "userPg";
    String password = "userPg";
    DbConnection customConnection = new DbConnection(url, user, password);
    QueryHandler queryHandlerCustom = new QueryHandler(customConnection.getConnection());
    DbConnection connection = new DbConnection();
    QueryHandler queryHandler = new QueryHandler(connection.getConnection());

    @BeforeEach
    void initTable() {
        queryHandler.ddlQueryProcessor(SqlQueries.CREATE_TABLE_BEFORE);
    }

    @AfterEach
    void clean() {
        queryHandler.ddlQueryProcessor(SqlQueries.DROP_TABLE_BEFORE);
    }

    @Test
    void testDdlQueryCreateTable() throws SQLException {
        String query = SqlQueries.CREATE_TABLE_PERSONS;
        int result = queryHandler.ddlQueryProcessor(query);
        assertEquals(0, result);
    }

    @Test
    void testDdlQueryDropTable() {
        queryHandler.ddlQueryProcessor(SqlQueries.CREATE_TABLE_PERSONS);
        queryHandler.ddlQueryProcessor(SqlQueries.DROP_TABLE_PERSONS);
    }

    @Test
    void testDdlQueryDropTableCustom() {
        queryHandlerCustom.ddlQueryProcessor(SqlQueries.CREATE_TABLE_PERSONS);
        queryHandlerCustom.ddlQueryProcessor(SqlQueries.DROP_TABLE_PERSONS);
    }

    @Test
    void testDmlSelect() {
        queryHandler.ddlQueryProcessor(SqlQueries.INSERT_INTO_BEFORE_TABLE);
        var result = queryHandler.dmlQueryProcessor("SELECT * FROM Before");
        String expected = "{PERSONID=[1, 2], LASTNAME=[Fedorov, Nikitchenko]}";
        String actual = result.toString();

        assertEquals(expected, actual);
    }

    @Test
    void testDmlSelectWHERE() {
        queryHandler.ddlQueryProcessor(SqlQueries.INSERT_INTO_BEFORE_TABLE);
        var result = queryHandler.dmlQueryProcessor("SELECT * FROM Before WHERE LASTNAME = 'Fedorov'");
        String expected = "{PERSONID=[1], LASTNAME=[Fedorov]}";
        String actual = result.toString();
        assertEquals(expected, actual);
    }

    @Test
    void selectFromEmptyTable() {
        var result = queryHandler.dmlQueryProcessor("SELECT * FROM Before");
        String expected = "{PERSONID=[], LASTNAME=[]}";
        String actual = result.toString();

        assertEquals(expected, actual);
    }

}