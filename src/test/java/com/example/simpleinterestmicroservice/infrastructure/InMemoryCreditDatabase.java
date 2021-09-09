package com.example.simpleinterestmicroservice.infrastructure;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class InMemoryCreditDatabase {
    private final static String DB_URL = "jdbc:h2:mem:test;" +
            "DATABASE_TO_UPPER=FALSE;" +
            "DB_CLOSE_DELAY=-1;" +
            "INIT=CREATE SCHEMA IF NOT EXISTS dbo";
    private static DataSource instance;


    public static DataSource getDataSource() {
        if (instance == null) {
            instance = initialize();
        }
        return instance;
    }

    private static DataSource initialize() {
        JdbcDataSource inMemoryDataSource = new JdbcDataSource();
        inMemoryDataSource.setUrl(DB_URL);

        createTables(inMemoryDataSource);

        return inMemoryDataSource;
    }

    private static void createTables(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.createStatement().executeUpdate(
                    "CREATE TABLE credits (\n" +
                            "    id VARCHAR(36) NOT NULL,\n" +
                            "    amount DECIMAL NOT NULL,\n" +
                            "    terms INT NOT NULL,\n" +
                            "    rate DECIMAL NOT NULL,\n" +
                            "    creation_date DATE NOT NULL \n" +
                            ");"
            );


            connection.createStatement().executeUpdate(
                    "CREATE TABLE payments (\n" +
                            "    parent_id VARCHAR(36) NOT NULL,\n" +
                            "    amount DECIMAL NOT NULL,\n" +
                            "    due_date DATE NOT NULL \n" +
                            ");"
            );
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
