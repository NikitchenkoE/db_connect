package constants;

public interface DbConstants {
    String DEFAULT_URL = "jdbc:h2:mem:db";
    String DEFAULT_USER = "user";
    String DEFAULT_PASSWORD = "password";

    String HTML_HEAD = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Query Result</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<table>\n";

    String HTML_FOOT = "</table>\n" +
            "</body>\n" +
            "</html>";

}
