package ru.akirakozov.sd.refactoring.servlet;

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

        switch (command) {
            case "max": {
                Product product = productService.getProductWithBiggestPrice();
                response.getWriter().print(surroundTags("<h1>Product with max price: </h1>\n",
                        product.getName() + "\t" + product.getPrice() + "</br>" + "\n"));
                break;
            }
            case "min": {
                Product product = productService.getProductWithLowesPrice();
                response.getWriter().print(surroundTags("<h1>Product with min price: </h1>\n",
                        product.getName() + "\t" + product.getPrice() + "</br>" + "\n"));
                break;
            }
            case "sum":
                long productsPriceSum = productService.getProductsPriceSum();
                response.getWriter().print(surroundTags("Summary price: \n", productsPriceSum + "\n"));
                break;
            case "count":
                long number = productService.getProductsAmount();
                response.getWriter().print(surroundTags("Number of products: \n", number + "\n"));
                break;
            default:
                response.getWriter().println("Unknown command: " + command);
                break;
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
