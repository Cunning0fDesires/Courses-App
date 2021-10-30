package com.epam.project.webappcourses.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {
    private static BasicDataSource ds = new BasicDataSource();
    private static Properties properties = new Properties();
    private static String user = getProperty("db.user");
    private static String password = getProperty("db.password");
    private static String host = getProperty("db.host");
    private static String port = getProperty("db.port");
    private static String dbName = getProperty("db.dbName");
    private static String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName);

    static {
        ds.setUrl(url);
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }
    public synchronized static String getProperty(String key) {
        if (properties.isEmpty()) {
            try (InputStream is = ConnectionPool.class.getClassLoader()
                    .getResourceAsStream("app.properties")) {
                properties.load(is);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return properties.getProperty(key);
    }

    public synchronized static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private ConnectionPool()
    { }
}
