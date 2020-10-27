package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.html.HtmlProcessor;
import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractSDServlet {
    public QueryServlet(ProductService productService) {
        super(productService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        HtmlProcessor responseHtml = new HtmlProcessor();
        switch (command) {
            case "max": {
                Product product = productService.getProductWithBiggestPrice();
                responseHtml.headerH1("Product with max price: ")
                        .line(product.getName() + "\t" + product.getPrice() + "</br>");
                break;
            }
            case "min": {
                Product product = productService.getProductWithLowesPrice();
                responseHtml.headerH1("Product with min price: ")
                        .line(product.getName() + "\t" + product.getPrice() + "</br>");
                break;
            }
            case "sum":
                long productsPriceSum = productService.getProductsPriceSum();
                responseHtml.line("Summary price: ")
                        .line(productsPriceSum + "");
                break;
            case "count":
                long number = productService.getProductsAmount();
                responseHtml.line("Number of products: ")
                        .line(number + "");
                break;
            default:
                responseHtml.line("Unknown command: " + command)
                    .withNoSurrounding();
                break;
        }
        response.getWriter().print(responseHtml.toHtml());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
