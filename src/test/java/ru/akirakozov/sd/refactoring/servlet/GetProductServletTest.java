package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class GetProductServletTest extends ProductServletTest {

    @Test
    public void empty() throws IOException {
        new GetProductsServlet().doGet(request, response);
        Assert.assertEquals("<html><body>\n</body></html>\n", writer.toString());
    }

    @Test
    public void someProductsTest() throws IOException {
        sqlExecutor.execute("INSERT INTO PRODUCT(NAME, PRICE) VALUES ('phone', 1000), ('mouse', 100), ('keyboard', 150)");
        new GetProductsServlet().doGet(request, response);
        Assert.assertEquals(
                "<html><body>\nphone\t1000</br>\nmouse\t100</br>\nkeyboard\t150</br>\n</body></html>\n",
                writer.toString());
    }
}
