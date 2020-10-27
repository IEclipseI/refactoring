package ru.akirakozov.sd.refactoring.sqlexecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Map<String, String>> select(String sql, List<String> fields) {
        List<Map<String, String>> result = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(database)) {
            Statement stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                Map<String, String> curProduct = new HashMap<>();
                for (String field : fields) {
                    curProduct.put(field, resultSet.getString(field));
                }
                result.add(curProduct);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
