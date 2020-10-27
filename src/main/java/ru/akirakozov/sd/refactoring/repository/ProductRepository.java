package ru.akirakozov.sd.refactoring.repository;

import ru.akirakozov.sd.refactoring.model.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();
    void save(Product product);
    void createTableIfNotExists();
    void dropDatabase();
}
