package ru.akirakozov.sd.refactoring.sqlexecutor;

import java.util.List;

public interface SqlExecutor {
    void execute(String sql);
    void select(String sql, List<String> fields);
}
