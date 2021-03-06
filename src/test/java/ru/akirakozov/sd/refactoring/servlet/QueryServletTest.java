package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.model.Product;

import java.io.IOException;

import static org.mockito.Mockito.when;

public class QueryServletTest extends ProductServletTest {

    @Before
    public void fillDB() {
        productService.saveProduct(new Product("p1", 10));
        productService.saveProduct(new Product("p2", 20));
        productService.saveProduct(new Product("p3", 25));
        productService.saveProduct(new Product("p4", 30));
    }

    private void doGet() throws IOException {
        new QueryServlet(productService).doGet(request, response);
    }

    @Test
    public void maxTest() throws IOException {
        when(request.getParameter("command")).thenReturn("max");
        doGet();
        String expected =
                "<html><body>\n" +
                "<h1>Product with max price: </h1>\n" +
                "p4\t30</br>\n" +
                "</body></html>\n";
        Assert.assertEquals(expected, writer.toString());
    }

    @Test
    public void minTest() throws IOException {
        when(request.getParameter("command")).thenReturn("min");
        doGet();
        String expected =
                "<html><body>\n" +
                "<h1>Product with min price: </h1>\n" +
                "p1\t10</br>\n" +
                "</body></html>\n";
        Assert.assertEquals(expected, writer.toString());
    }

    @Test
    public void sumTest() throws IOException {
        when(request.getParameter("command")).thenReturn("sum");
        doGet();
        String expected =
                "<html><body>\n" +
                "Summary price: \n" +
                "85\n" +
                "</body></html>\n";
        Assert.assertEquals(expected, writer.toString());
    }

    @Test
    public void countTest() throws IOException {
        when(request.getParameter("command")).thenReturn("count");
        doGet();
        String expected =
                "<html><body>\n" +
                "Number of products: \n" +
                "4\n" +
                "</body></html>\n";
        Assert.assertEquals(expected, writer.toString());
    }

    @Test
    public void empty() throws IOException {
        when(request.getParameter("command")).thenReturn("");
        doGet();
        Assert.assertEquals("Unknown command: \n", writer.toString());
    }

    @Test
    public void unknownCommand() throws IOException {
        when(request.getParameter("command")).thenReturn("kekw");
        doGet();
        Assert.assertEquals("Unknown command: kekw\n", writer.toString());
    }
}
