package entity;

import java.util.List;

public class QueryResult {
    private String columnName;
    private List<Object> rows;

    public QueryResult(String columnName, List<Object> rows) {
        this.columnName = columnName;
        this.rows = rows;
    }

    public String getColumnName() {
        return columnName;
    }

    public List<Object> getRows() {
        return rows;
    }

    @Override
    public String toString() {
        return columnName + "=" +rows.toString();
    }
}