package by.javaguru.servlet;

import by.javaguru.service.UserService;
import by.javaguru.dto.UserReadDto;
import by.javaguru.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

import static by.javaguru.utils.UrlPath.LOGIN;
import static by.javaguru.utils.UrlPath.PRODUCTS;

@WebServlet(LOGIN)
public class LoginServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.trace("Session {} went to {} page", req.getSession(), LOGIN);
        req.getRequestDispatcher(JspHelper.getPath(LOGIN)).forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        log.trace("Session {} tried email ({}, {})", req.getSession(),
                req.getParameter("email"), req.getParameter("password"));
        userService.login(req.getParameter("email"), req.getParameter("password"))
                .ifPresentOrElse(userDto -> onLoginSuccess(userDto, req, resp),
                        () -> onLoginFail(req, resp));
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/login?error&email=" + req.getParameter("email"));
        log.warn("Session {} failed email ({}, {})", req.getSession(),
                req.getParameter("email"), req.getParameter("password"));
    }

    @SneakyThrows
    private void onLoginSuccess(UserReadDto userDto, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", userDto);
        resp.sendRedirect(PRODUCTS);
        log.trace("Session {} was authorized as {}", req.getSession(), userDto);
    }
}
