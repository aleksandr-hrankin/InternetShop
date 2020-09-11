package com.internet.shop.controller.product;

import com.internet.shop.lib.Injector;
import com.internet.shop.service.interfaces.ProductService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProductController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("productId"));
        productService.delete(productId);
        resp.sendRedirect(req.getContextPath() + "/storage/products");
    }
}
