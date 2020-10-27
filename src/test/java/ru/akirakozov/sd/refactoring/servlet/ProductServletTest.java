package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.sqlexecutor.SqlExecutor;
import ru.akirakozov.sd.refactoring.sqlexecutor.SqlExecutorImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

public class ProductServletTest {
    protected static final String DATABASE = "jdbc:sqlite:test.db";
    protected SqlExecutor sqlExecutor= new SqlExecutorImpl(DATABASE);
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
        sqlExecutor.execute("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    @After
    public void dropTestDatabase() {
       sqlExecutor.execute("DROP TABLE IF EXISTS PRODUCT");
    }

    protected List<Map<String, String>> selectProducts(String sql) {
        return sqlExecutor.select(sql, List.of("name", "price"));
    }

    protected void addProduct(String name, String price) throws IOException {
        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("price")).thenReturn(price);
        new AddProductServlet().doGet(request, response);
    }
}
