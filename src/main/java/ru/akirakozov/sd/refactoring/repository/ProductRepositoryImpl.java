package ru.akirakozov.sd.refactoring.repository;

import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.sqlexecutor.SqlExecutor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepository {
    private final SqlExecutor sqlExecutor;

    public ProductRepositoryImpl(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Map<String, String>> result = sqlExecutor.select("SELECT * FROM products", List.of("name", "price"));
        return result.stream()
                .map(m -> new Product(m.get("name"), Integer.parseInt(m.get("price"))))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        sqlExecutor.execute("INSERT INTO PRODUCT (NAME, PRICE) VALUES ('" + product.getName() + "','" + product.getPrice() + "')");
    }

    @Override
    public void createTableIfNotExists() {
        sqlExecutor.execute(
                "CREATE TABLE IF NOT EXISTS PRODUCT" +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        " NAME           TEXT    NOT NULL, " +
                        " PRICE          INT     NOT NULL)");
    }
}
