package db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import constants.DbConstants;

public class DataSource {
    private String url = DbConstants.DEFAULT_URL;
    private String user = DbConstants.DEFAULT_USER;
    private String password = DbConstants.DEFAULT_PASSWORD;

    public DataSource(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public DataSource() {
    }

    public javax.sql.DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(user);
        config.setPassword(password);
        config.setJdbcUrl(url);
        return new HikariDataSource(config);
    }
}
