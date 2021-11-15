package services;

import db.DataSource;
import db.QueryHandler;
import lombok.extern.slf4j.Slf4j;

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
            return queryHandler.selectCommandHandler(query);
        } else {
            return queryHandler.updateCommandHandler(query);
        }
    }


}
