package ru.akirakozov.sd.refactoring.sqlexecutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SqlExecutorImpl implements SqlExecutor {
    private final String database;

    public SqlExecutorImpl(String database) {
        this.database = database;
    }

    @Override
    public void execute(String sql) {
        try (Connection c = DriverManager.getConnection(database)) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void select(String sql, List<String> fields) {

    }
}
