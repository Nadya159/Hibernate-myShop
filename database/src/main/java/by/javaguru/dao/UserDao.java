package by.javaguru.dao;

import by.javaguru.entity.User;
import by.javaguru.utils.HibernateUtil;
import lombok.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao extends DaoBase<Integer, User> {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final static UserDao INSTANCE = new UserDao();

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        @Cleanup var session = sessionFactory.openSession();
        var query = session.createQuery("select u from User u where u.email = :email and u.password = :password")
                .setParameter("email", email)
                .setParameter("password", password);
        User user = (User) query.uniqueResult();
        return Optional.ofNullable(user);
    }

    /**
     * Количество пользователей каждого гендера
     */
    public List<Object[]> findUserInGender(Session session) {
        return session.createQuery("select u.gender, count(u.id) " +
                                   "from User u " +
                                   "group by u.gender", Object[].class)
                .list();
    }


    public static UserDao getInstance() {
        return INSTANCE;
    }
}
