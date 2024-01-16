package by.javaguru.servlet;

import by.javaguru.service.UserService;
import by.javaguru.dto.UserCreateDto;
import by.javaguru.entity.Gender;
import by.javaguru.entity.Role;
import by.javaguru.exception.ValidationException;
import by.javaguru.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;

import static by.javaguru.utils.UrlPath.LOGIN;
import static by.javaguru.utils.UrlPath.REGISTRATION;


@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());
        req.getRequestDispatcher(JspHelper.getPath(REGISTRATION)).forward(req, resp);
        log.trace("Session {} went to {} page", req.getSession(), REGISTRATION);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userCreateDto = new UserCreateDto(
                req.getParameter("name"),
                req.getParameter("phone"),
                req.getParameter("email"),
                Role.valueOf(req.getParameter("role")),
                Gender.valueOf(req.getParameter("gender")),
                LocalDate.parse(req.getParameter("birthday")),
                req.getParameter("pwd")
                );
        try {
            userService.create(userCreateDto);
            resp.sendRedirect(LOGIN);
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
        log.trace("Session {} tried to sing in {}", req.getSession(), userCreateDto);
    }
}
