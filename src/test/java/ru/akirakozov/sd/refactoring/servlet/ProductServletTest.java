package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class ProductServletTest {
    protected static final String DATABASE = "jdbc:sqlite:test.db";
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
    public void createTestDatabase() throws SQLException {
        execSql("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    @After
    public void dropTestDatabase() throws SQLException {
        execSql("DROP TABLE IF EXISTS PRODUCT");
    }

    protected void execSql(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(DATABASE)) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    protected List<List<String>> selectProducts(String sql) throws SQLException {
        return select(sql, List.of("name", "price"));
    }

    protected List<List<String>> select(String sql, List<String> fields) throws SQLException {
        List<List<String>> result = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(DATABASE)) {
            Statement stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                List<String> curProduct = new ArrayList<>();
                for (String field : fields) {
                    curProduct.add(resultSet.getString(field));
                }
                result.add(curProduct);
            }
            stmt.close();
        }
        return result;
    }
    protected void addProduct(String name, String price) throws IOException {
        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("price")).thenReturn(price);
        new AddProductServlet().doGet(request, response);
    }
}
