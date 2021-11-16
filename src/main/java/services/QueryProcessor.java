package services;

import db.DataSource;
import db.QueryHandler;
import entity.QueryResult;
import lombok.extern.slf4j.Slf4j;
import responseProcesoors.FileSaver;
import responseProcesoors.HtmlRender;
import responseProcesoors.TableRender;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class QueryProcessor {
    private final DataSource dataSource;
    private final Pattern patternDeleteSpaces = Pattern.compile(" ");


    public QueryProcessor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Object processQuery(String query) {
        QueryHandler queryHandler = new QueryHandler(dataSource.getDataSource());
        log.info(query);
        String[] wordsFromQuery = patternDeleteSpaces.split(query);
        String commandFromQuery = wordsFromQuery[0];
        if (commandFromQuery.equals("SELECT")) {
            return selectCommand(queryHandler, query);
        } else {
            return queryHandler.updateCommandHandler(query);
        }
    }

    private String selectCommand(QueryHandler queryHandler, String query) {
        List<QueryResult> queryResults = queryHandler.selectCommandHandler(query);
        TableRender tableRender = new TableRender(queryResults);
        HtmlRender htmlRender = new HtmlRender(queryResults);
        FileSaver fileSaver = new FileSaver();
        String renderedTable = tableRender.renderTable();
        String content = htmlRender.renderHtmlTable();
        String pathToFile = fileSaver.save("src/main/resources/results", content);
        String messageToClient = String.format("Path to file with query result in html format by path - %s", pathToFile);

        return renderedTable.concat("\n").concat(messageToClient);
    }


}
