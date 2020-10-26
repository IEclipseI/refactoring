package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.when;

public class GetProductServletTest {
    private static final String DATABASE = "jdbc:sqlite:test.db";
    private final StringWriter writer = new StringWriter();
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;

    private void execSql(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(DATABASE)) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

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

    @Test
    public void empty() throws IOException {
        new GetProductsServlet().doGet(request, response);
        Assert.assertEquals("<html><body>\n</body></html>\n", writer.toString());
    }

    @Test
    public void someProductsTest() throws IOException, SQLException {
        execSql("INSERT INTO PRODUCT(NAME, PRICE) VALUES ('phone', 1000), ('mouse', 100), ('keyboard', 150)");
        new GetProductsServlet().doGet(request, response);
        Assert.assertEquals(
                "<html><body>\nphone\t1000</br>\nmouse\t100</br>\nkeyboard\t150</br>\n</body></html>\n",
                writer.toString());
    }
}
