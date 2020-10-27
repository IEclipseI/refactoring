package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.repository.ProductRepository;
import ru.akirakozov.sd.refactoring.repository.ProductRepositoryImpl;
import ru.akirakozov.sd.refactoring.service.ProductServiceImpl;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;
import ru.akirakozov.sd.refactoring.sqlexecutor.SqlExecutorImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class Main {
    static final String database = System.getenv("jdbcUrl");

    public static void main(String[] args) throws Exception {
        SqlExecutorImpl sqlExecutor = new SqlExecutorImpl(database);
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl(sqlExecutor);
        ProductServiceImpl productService = new ProductServiceImpl(productRepository);
        productService.createTableIfNotExists();

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(productService)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(productService)),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(productService)),"/query");

        server.start();
        server.join();
    }
}
