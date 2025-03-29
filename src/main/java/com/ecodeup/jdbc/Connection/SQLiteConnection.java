package com.ecodeup.jdbc.Connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteConnection {

    private static  final DataSource dataSource;
    private static final String url = "jdbc:sqlite:banco";

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
