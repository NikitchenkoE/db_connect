package services;

import db.DataSource;
import db.QueryHandler;
import entity.DmlQuery;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Slf4j
public class QueryProcessor {
    private final DataSource dataSource;
    private final Pattern patternDeleteSpaces = Pattern.compile(" ");


    public QueryProcessor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Object getResult(String query) {
        QueryHandler queryHandler = new QueryHandler(dataSource.getDataSource());
        log.info(query);
        String[] wordsFromQuery = patternDeleteSpaces.split(query);
        String commandFromQuery = wordsFromQuery[0];
        if (isDml(commandFromQuery)) {
            return queryHandler.dmlQueryProcessor(query);
        } else {
            return queryHandler.ddlQueryProcessor(query);
        }
    }

    private boolean isDml(String commandFromQuery) {
        DmlQuery[] valuesDml = DmlQuery.values();
        boolean result = false;
        for (DmlQuery dmlQuery : valuesDml) {
            if (dmlQuery.toString().equals(commandFromQuery)) {
                result = true;
            }
        }
        return result;
    }
}
