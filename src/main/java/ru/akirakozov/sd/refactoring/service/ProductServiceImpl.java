package ru.akirakozov.sd.refactoring.service;

import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.repository.ProductRepository;

import java.util.Comparator;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createTableIfNotExists() {
        repository.createTableIfNotExists();
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.getAllProducts();
    }

    @Override
    public Product getProductWithBiggestPrice() {
        return repository.getAllProducts()
                .stream()
                .max(Comparator.comparing(Product::getPrice))
                .orElse(null);
    }

    @Override
    public Product getProductWithLowesPrice() {
        return repository.getAllProducts()
                .stream()
                .min(Comparator.comparing(Product::getPrice))
                .orElse(null);
    }

    @Override
    public long getProductsPriceSum() {
        return repository.getAllProducts()
                .stream()
                .mapToLong(Product::getPrice)
                .sum();
    }

    @Override
    public int getProductsAmount() {
        return repository.getAllProducts().size();
    }

    @Override
    public void saveProduct(Product product) {
        repository.save(product);
    }
}
