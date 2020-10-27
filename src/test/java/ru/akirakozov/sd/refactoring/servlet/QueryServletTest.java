package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class QueryServletTest extends ProductServletTest {

    @Before
    public void fillDB() {
        insertProduct("p1", "10");
        insertProduct("p2", "20");
        insertProduct("p3", "25");
        insertProduct("p4", "30");
    }

    private void insertProduct(String name, String price) {
        sqlExecutor.execute("INSERT INTO PRODUCT (NAME, PRICE) VALUES ('" + name + "','" + price + "')");
    }


    @Test
    public void maxTest() throws IOException {
        when(request.getParameter("command")).thenReturn("max");
        new QueryServlet().doGet(request, response);
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
        new QueryServlet().doGet(request, response);
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
        new QueryServlet().doGet(request, response);
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
        new QueryServlet().doGet(request, response);
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
        new QueryServlet().doGet(request, response);
        Assert.assertEquals("Unknown command: \n", writer.toString());
    }

    @Test
    public void unknownCommand() throws IOException {
        when(request.getParameter("command")).thenReturn("kekw");
        new QueryServlet().doGet(request, response);
        Assert.assertEquals("Unknown command: kekw\n", writer.toString());
    }
}
