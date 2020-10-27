package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.html.HtmlProcessor;
import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractSDServlet {

    public GetProductsServlet(ProductService productService) {
        super(productService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> allProducts = productService.getAllProducts();
        StringBuilder sb = new StringBuilder();
        for (Product product : allProducts) {
            sb.append(product.getName()).append("\t").append(product.getPrice()).append("</br>\n");
        }
        response.getWriter().print(new HtmlProcessor().text(sb.toString()).toHtml());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
