package services;

import db.DataSource;
import db.SqlQueries;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryProcessorTest {
DataSource dataSource = new DataSource();
QueryProcessor queryProcessor = new QueryProcessor(dataSource);

    @BeforeEach
    void initTable() {
        queryProcessor.processQuery(SqlQueries.CREATE_TABLE_BEFORE);
    }

    @AfterEach
    void clean() {
        queryProcessor.processQuery(SqlQueries.DROP_TABLE_BEFORE);
    }

    @Test
    void getResultDDL() {
        String query = SqlQueries.CREATE_TABLE_PERSONS;
        var result = queryProcessor.processQuery(query);
        assertEquals(0,result);
    }

    @Test
    void getResultDML(){
        queryProcessor.processQuery(SqlQueries.INSERT_INTO_BEFORE_TABLE);
        var result = queryProcessor.processQuery("SELECT * FROM Before WHERE LASTNAME = 'Fedorov'");
        String expected = "[PERSONID=[1], LASTNAME=[Fedorov]]";
        String actual = result.toString();
        assertEquals(expected, actual);
    }
}