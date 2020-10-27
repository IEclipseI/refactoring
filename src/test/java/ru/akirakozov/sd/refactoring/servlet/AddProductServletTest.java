package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddProductServletTest extends ProductServletTest {

    @Test
    public void addOneProduct() throws IOException, SQLException {
        addProduct("iPhone 322", "666666");
        Assert.assertEquals("OK\n", writer.toString());
        List<List<String>> products = selectProducts("SELECT name, price FROM product");
        Assert.assertEquals(products.get(0), List.of("iPhone 322", "666666"));
    }

    @Test
    public void addSomeProducts() throws IOException, SQLException {
        addProduct("iPhone 322", "666666");
        Assert.assertEquals("OK\n", writer.toString());
        addProduct("house", "777");
        Assert.assertEquals("OK\nOK\n", writer.toString());

        List<List<String>> products = selectProducts("SELECT name, price FROM product");
        Assert.assertEquals(products.get(0), List.of("iPhone 322", "666666"));
        Assert.assertEquals(products.get(1), List.of("house", "777"));
    }
}
