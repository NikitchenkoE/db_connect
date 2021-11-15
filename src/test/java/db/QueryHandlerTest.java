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
        queryHandler.updateCommandHandler(SqlQueries.CREATE_TABLE_BEFORE);
    }

    @AfterEach
    void clean() {
        queryHandler.updateCommandHandler(SqlQueries.DROP_TABLE_BEFORE);
    }

    @Test
    void testDdlQueryCreateTable() {
        String query = SqlQueries.CREATE_TABLE_PERSONS;
        int result = queryHandler.updateCommandHandler(query);
        assertEquals(0, result);
    }

    @Test
    void testDdlQueryDropTable() {
        queryHandler.updateCommandHandler(SqlQueries.CREATE_TABLE_PERSONS);
        queryHandler.updateCommandHandler(SqlQueries.DROP_TABLE_PERSONS);
    }

    @Test
    void testDdlQueryInsertIntoReturnNumberOfChangedLines() {
        var result = queryHandler.updateCommandHandler(SqlQueries.INSERT_INTO_BEFORE_TABLE);
        var result2 = queryHandler.updateCommandHandler("INSERT INTO Before VALUES (3, 'Lavrov')");
        assertEquals(2, result);
        assertEquals(1, result2);
    }

    @Test
    void testDdlQueryDropTableCustom() {
        queryHandlerCustom.updateCommandHandler(SqlQueries.CREATE_TABLE_PERSONS);
        queryHandlerCustom.updateCommandHandler(SqlQueries.DROP_TABLE_PERSONS);
    }

    @Test
    void testDmlSelect() {
        queryHandler.updateCommandHandler(SqlQueries.INSERT_INTO_BEFORE_TABLE);
        var result = queryHandler.selectCommandHandler("SELECT * FROM Before");
        String expected = "[PERSONID=[1, 2], LASTNAME=[Fedorov, Nikitchenko]]";
        String actual = result.toString();

        assertEquals(expected, actual);
    }

    @Test
    void testDmlSelectWHERE() {
        queryHandler.updateCommandHandler(SqlQueries.INSERT_INTO_BEFORE_TABLE);
        var result = queryHandler.selectCommandHandler("SELECT * FROM Before WHERE LASTNAME = 'Fedorov'");
        String expected = "[PERSONID=[1], LASTNAME=[Fedorov]]";
        String actual = result.toString();
        assertEquals(expected, actual);
    }

    @Test
    void selectFromEmptyTable() {
        var result = queryHandler.selectCommandHandler("SELECT * FROM Before");
        String expected = "[PERSONID=[], LASTNAME=[]]";
        String actual = result.toString();

        assertEquals(expected, actual);
    }

    @Test
    void testAlterTable(){
        var result = queryHandler.updateCommandHandler(SqlQueries.ALTER_TABLE_BEFORE);
        var expected = 0;
        assertEquals(expected,result);
    }

    @Test
    void testRenameTable() {
        queryHandler.updateCommandHandler(SqlQueries.INSERT_INTO_BEFORE_TABLE);
        queryHandler.updateCommandHandler(SqlQueries.RENAME_TABLE_BEFORE);
        var result = queryHandler.selectCommandHandler("SELECT * FROM new_table_name WHERE LASTNAME = 'Fedorov'");
        queryHandler.updateCommandHandler(SqlQueries.RENAME_TABLE_BACK);
        String expected = "[PERSONID=[1], LASTNAME=[Fedorov]]";
        String actual = result.toString();
        assertEquals(expected, actual);
    }

}