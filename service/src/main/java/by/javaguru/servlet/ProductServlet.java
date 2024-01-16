package by.javaguru.servlet;

import by.javaguru.service.ProductService;
import by.javaguru.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static by.javaguru.utils.UrlPath.PRODUCTS;

@WebServlet(PRODUCTS)
public class ProductServlet extends HttpServlet {
    private final ProductService productService = ProductService.getInstance();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        req.setAttribute("products", productService.findAll());
        req.getRequestDispatcher(JspHelper.getPath(PRODUCTS)).forward(req,resp);
        log.trace("Session {} went to {} page", req.getSession(), PRODUCTS);
    }
}
