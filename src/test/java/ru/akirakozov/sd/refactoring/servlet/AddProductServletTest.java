package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.model.Product;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddProductServletTest extends ProductServletTest {

    @Test
    public void addOneProduct() throws IOException {
        addProduct("iPhone 322", "666666");
        Assert.assertEquals("OK\n", writer.toString());
        List<Product> products = productService.getAllProducts();
        Assert.assertEquals(products.get(0), new Product("iPhone 322",666666));
    }

    @Test
    public void addSomeProducts() throws IOException {
        addProduct("iPhone 322", "666666");
        Assert.assertEquals("OK\n", writer.toString());
        addProduct("house", "777");
        Assert.assertEquals("OK\nOK\n", writer.toString());

        List<Product> products = productService.getAllProducts();
        Assert.assertEquals(products.get(0), new Product("iPhone 322",666666));
        Assert.assertEquals(products.get(1), new Product("house",777));
    }
}
