package db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryHandlerTest {

    String url = "jdbc:postgresql:postgreDb";
    String user = "userPg";
    String password = "userPg";
    DataSource customDataSource = new DataSource(url, user, password);
    QueryHandler queryHandlerCustom = new QueryHandler(customDataSource.getDataSource());
    DataSource dataSource = new DataSource();
    QueryHandler queryHandler = new QueryHandler(dataSource.getDataSource());

    @BeforeEach
    void initTable() {
        queryHandler.ddlQueryProcessor(SqlQueries.CREATE_TABLE_BEFORE);
    }

    @AfterEach
    void clean() {
        queryHandler.ddlQueryProcessor(SqlQueries.DROP_TABLE_BEFORE);
    }

    @Test
    void testDdlQueryCreateTable() {
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
    void testDdlQueryInsertIntoReturnNumberOfChangedLines() {
        var result = queryHandler.ddlQueryProcessor(SqlQueries.INSERT_INTO_BEFORE_TABLE);
        var result2 = queryHandler.ddlQueryProcessor("INSERT INTO Before VALUES (3, 'Lavrov')");
        assertEquals(2, result);
        assertEquals(1, result2);
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
        String expected = "[PERSONID=[1, 2], LASTNAME=[Fedorov, Nikitchenko]]";
        String actual = result.toString();

        assertEquals(expected, actual);
    }

    @Test
    void testDmlSelectWHERE() {
        queryHandler.ddlQueryProcessor(SqlQueries.INSERT_INTO_BEFORE_TABLE);
        var result = queryHandler.dmlQueryProcessor("SELECT * FROM Before WHERE LASTNAME = 'Fedorov'");
        String expected = "[PERSONID=[1], LASTNAME=[Fedorov]]";
        String actual = result.toString();
        assertEquals(expected, actual);
    }

    @Test
    void selectFromEmptyTable() {
        var result = queryHandler.dmlQueryProcessor("SELECT * FROM Before");
        String expected = "[PERSONID=[], LASTNAME=[]]";
        String actual = result.toString();

        assertEquals(expected, actual);
    }

}