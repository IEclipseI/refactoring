package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.model.Product;

import java.io.IOException;
import java.util.List;

public class GetProductServletTest extends ProductServletTest {

    @Test
    public void empty() throws IOException {
        new GetProductsServlet(productService).doGet(request, response);
        Assert.assertEquals("<html><body>\n</body></html>\n", writer.toString());
    }

    @Test
    public void someProductsTest() throws IOException {
        productService.saveAll(
                List.of(
                        new Product("phone", 1000),
                        new Product("mouse", 100),
                        new Product("keyboard", 150)));
        new GetProductsServlet(productService).doGet(request, response);
        Assert.assertEquals(
                "<html><body>\nphone\t1000</br>\nmouse\t100</br>\nkeyboard\t150</br>\n</body></html>\n",
                writer.toString());
    }
}
