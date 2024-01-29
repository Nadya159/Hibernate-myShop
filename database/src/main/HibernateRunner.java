package by.javaguru;

import by.javaguru.dao.UserRepository;
import by.javaguru.dto.UserCreateDto;
import by.javaguru.entity.Gender;
import by.javaguru.entity.Role;
import by.javaguru.service.UserService;
import by.javaguru.utils.HibernateUtil;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory()) {
            var session = sessionFactory.getCurrentSession();
            /*var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
                    new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));*/
            session.beginTransaction();
            UserRepository userDao = UserRepository.getInstance();
            userDao.findById(2).ifPresent(System.out::println);
            var userCreateDto = new UserCreateDto(null, "79068777167", "test@mail.ru",
                    Role.USER, Gender.MALE, LocalDate.of(2000, 01, 20), "123");
            UserService userService = UserService.getInstance();
            userService.create(userCreateDto);

            session.getTransaction().commit();
            session.close();
        }
    }
}
