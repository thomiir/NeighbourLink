package org.example.lab6.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.lab6.config.ApplicationContext;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl(ApplicationContext.getPROPERTIES().getProperty("DB_URL"));
        config.setUsername(ApplicationContext.getPROPERTIES().getProperty("DB_USERNAME"));
        config.setPassword(ApplicationContext.getPROPERTIES().getProperty("DB_PASSWORD"));
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}