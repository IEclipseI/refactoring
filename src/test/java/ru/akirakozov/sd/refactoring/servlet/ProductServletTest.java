package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.repository.ProductRepositoryImpl;
import ru.akirakozov.sd.refactoring.service.ProductServiceImpl;
import ru.akirakozov.sd.refactoring.sqlexecutor.SqlExecutor;
import ru.akirakozov.sd.refactoring.sqlexecutor.SqlExecutorImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

public class ProductServletTest {
    protected static final String database = System.getenv("testdb_jdbcUrl");
//    protected static final String database = "jdbc:sqlite:test.db";
    protected SqlExecutorImpl sqlExecutor = new SqlExecutorImpl(database);
    protected ProductRepositoryImpl productRepository = new ProductRepositoryImpl(sqlExecutor);
    protected ProductServiceImpl productService = new ProductServiceImpl(productRepository);

    protected StringWriter writer = new StringWriter();

    @Mock
    protected HttpServletResponse response;
    @Mock
    protected HttpServletRequest request;

    @Before
    public void setUpMocks() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Before
    public void createTestDatabase() {
        productService.createTableIfNotExists();
    }

    @After
    public void dropTestDatabase() {
       productService.dropDatabase();
    }

    protected void addProduct(String name, String price) throws IOException {
        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("price")).thenReturn(price);
        new AddProductServlet().doGet(request, response);
    }
}
