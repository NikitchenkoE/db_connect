package entity;

import java.util.List;

public class QueryResult {
    private final String columnName;
    private final List<Object> values;

    public QueryResult(String columnName, List<Object> rows) {
        this.columnName = columnName;
        this.values = rows;
    }

    public String getColumnName() {
        return columnName;
    }

    public List<Object> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return columnName + "=" + values.toString();
    }
}
