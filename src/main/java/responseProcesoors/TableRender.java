package responseProcesoors;

import de.vandermeer.asciitable.AsciiTable;
import entity.QueryResult;

import java.util.ArrayList;
import java.util.List;

public class TableRender {
    private final List<QueryResult> queryResultList;
    private int size = 0;
    private int currentSize = 0;

    public TableRender(List<QueryResult> queryResultList) {
        this.queryResultList = queryResultList;
    }

    public String renderTable() {
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(getColumnNames());
        asciiTable.addRule();
        while (currentSize<size){
         asciiTable.addRow(getRows());
         asciiTable.addRule();
        }
        return asciiTable.render();
    }

    private List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>();
        for (QueryResult queryResult : queryResultList) {
            columnNames.add(queryResult.getColumnName());
            size = queryResult.getRows().size();
        }
        return columnNames;
    }

    private List<Object> getRows() {
        List<List<Object>> rowsLists = new ArrayList<>();
        for (QueryResult queryResult : queryResultList) {
            rowsLists.add(queryResult.getRows());
        }
        List<Object> row = new ArrayList<>();

        for (List<Object> rowsList : rowsLists) {
            row.add(rowsList.get(currentSize));
        }
        currentSize++;
        return row;
    }


}
