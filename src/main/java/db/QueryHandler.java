package db;

import entity.QueryResult;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryHandler {
    private DataSource dataSource;

    public QueryHandler(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int updateCommandHandler(String query) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public List<QueryResult> selectCommandHandler(String query) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            List<String> columnNames = getColumnNames(metaData);

            return getResult(resultSet, columnNames);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    @SneakyThrows
    private List<QueryResult> getResult(ResultSet resultSet, List<String> columnNames) {
        List<QueryResult> result = new ArrayList<>();
        for (String columnName : columnNames) {
            result.add(new QueryResult(columnName, new ArrayList<>()));
        }
        while (resultSet.next()) {
            for (String columnName : columnNames) {
                for (QueryResult thisQueryResult : result) {
                    if (thisQueryResult.getColumnName().equals(columnName)) {
                        thisQueryResult.getRows().add(resultSet.getObject(columnName));
                    }
                }
            }
        }

        return result;
    }

    @SneakyThrows
    private List<String> getColumnNames(ResultSetMetaData metaData) {
        List<String> columns = new ArrayList<>();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            columns.add(metaData.getColumnName(i));
        }
        return columns;
    }


}
