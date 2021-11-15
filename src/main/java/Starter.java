import db.DataSource;
import services.InputReader;
import services.QueryProcessor;

public class Starter {
    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        InputReader inputReader = new InputReader(System.in);
        QueryProcessor queryProcessor = new QueryProcessor(dataSource);

        while (true) {
            var result = queryProcessor.processQuery(inputReader.getStringFromConsole());
            System.out.println(result);
        }
    }
}
