package ru.akirakozov.sd.refactoring.service;

import ru.akirakozov.sd.refactoring.model.Product;

import java.util.List;

public interface ProductService {
    void createTableIfNotExists();
    List<Product> getAllProducts();
    Product getProductWithBiggestPrice();
    Product getProductWithLowesPrice();
    long getProductsPriceSum();
    int getProductsAmount();
    void saveProduct(Product product);
}
