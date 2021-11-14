package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryHandler {
    private final Connection connection;

    public QueryHandler(Connection connection) {
        this.connection = connection;
    }

    public int ddlQueryProcessor(String query) {
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public Map<String, List<String>> dmlQueryProcessor(String query) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            List<String> columnNames = getColumnNames(metaData);

            return getResult(resultSet, columnNames);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    private Map<String, List<String>> getResult(ResultSet resultSet, List<String> columnNames) throws SQLException {
        Map<String, List<String>> result = new HashMap<>();
        while (resultSet.next()) {
            for (String columnName : columnNames) {
                if (!result.containsKey(columnName)) {
                    result.put(columnName, new ArrayList<>());
                }
                result.get(columnName).add(resultSet.getString(columnName));
            }
        }
        if (result.size() == 0) {
            for (String columnName : columnNames) {
                result.put(columnName, new ArrayList<>());
            }
        }

        return result;
    }

    private List<String> getColumnNames(ResultSetMetaData metaData) throws SQLException {
        List<String> columns = new ArrayList<>();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            columns.add(metaData.getColumnName(i));
        }
        return columns;
    }

}
