package responseProcesoors;

import constants.DbConstants;
import entity.QueryResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class HtmlRender {
    private final List<QueryResult> queryResultList;
    private int size = 0;
    private int currentSize = 0;

    public HtmlRender(List<QueryResult> queryResultList) {
        this.queryResultList = queryResultList;
    }

    public String renderHtmlTable() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add(DbConstants.htmlFile);
        stringJoiner.add(getColumnNames());
        while (currentSize < size) {
            stringJoiner.add(getRows());
        }
        stringJoiner.add("</table>");
        stringJoiner.add("</body>");
        stringJoiner.add("</html>");

        return save("src/main/resources/results", stringJoiner.toString());
    }

    private String getColumnNames() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("<tr>");
        for (QueryResult queryResult : queryResultList) {
            stringJoiner.add(String.format("<th>%s</th>", queryResult.getColumnName()));
            size = queryResult.getValues().size();
        }
        stringJoiner.add("</tr>");
        return stringJoiner.toString();
    }

    private String getRows() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        List<List<Object>> rowsLists = new ArrayList<>();
        for (QueryResult queryResult : queryResultList) {
            rowsLists.add(queryResult.getValues());
        }
        List<Object> row = new ArrayList<>();

        stringJoiner.add("<tr>");
        for (List<Object> rowsList : rowsLists) {
            stringJoiner.add(String.format("<td>%s</td>", rowsList.get(currentSize).toString()));

        }
        stringJoiner.add("</tr>");
        currentSize++;

        return stringJoiner.toString();
    }

    private String save(String path, String content) {
        Timestamp ts = Timestamp.from(Instant.now());
        var partOfFileName = ts.getTime();
        String fileName = partOfFileName + ".html";
        File file = new File(path, fileName);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file.getAbsolutePath();
    }
}
