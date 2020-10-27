package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.service.ProductService;

import javax.servlet.http.HttpServlet;

public abstract class AbstractSDServlet extends HttpServlet {
    protected final ProductService productService;

    protected AbstractSDServlet(ProductService productService) {
        this.productService = productService;
    }
}
