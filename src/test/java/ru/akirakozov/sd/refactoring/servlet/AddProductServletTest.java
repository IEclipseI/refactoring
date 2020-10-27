package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddProductServletTest extends ProductServletTest {

    @Test
    public void addOneProduct() throws IOException {
        addProduct("iPhone 322", "666666");
        Assert.assertEquals("OK\n", writer.toString());
        List<Map<String, String>> products = selectProducts("SELECT name, price FROM product");
        Assert.assertEquals(products.get(0), Map.of("name", "iPhone 322","price", "666666"));
    }

    @Test
    public void addSomeProducts() throws IOException {
        addProduct("iPhone 322", "666666");
        Assert.assertEquals("OK\n", writer.toString());
        addProduct("house", "777");
        Assert.assertEquals("OK\nOK\n", writer.toString());

        List<Map<String, String>> products = selectProducts("SELECT name, price FROM product");
        Assert.assertEquals(products.get(0), Map.of("name", "iPhone 322","price", "666666"));
        Assert.assertEquals(products.get(1), Map.of("name", "house","price", "777"));
    }
}
