package ru.akirakozov.sd.refactoring.service;

import ru.akirakozov.sd.refactoring.model.Product;

public interface ProductService {
    void createTableIfNotExists();
    void getAllProducts();
    void getProductWithBiggestPrice();
    void getProductWithLowesPrice();
    void getProductsPriceSum();
    void getProductsAmount();
    void saveProduct(Product product);
}
