package ru.akirakozov.sd.refactoring.sqlexecutor;

import java.util.List;
import java.util.Map;

public interface SqlExecutor {
    void execute(String sql);
    List<Map<String, String>> select(String sql, List<String> fields);
}
