package by.javaguru.dao;

import by.javaguru.entity.User;
import by.javaguru.utils.HibernateUtil;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserDao implements Dao<Integer, User> {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final static UserDao INSTANCE = new UserDao();

    @Override
    public User save(User entity) {
        @Cleanup var session = sessionFactory.openSession();
        session.persist(entity);
        return entity;
    }

    @Override
    public boolean delete(Integer id) {
        @Cleanup var session = sessionFactory.openSession();
        session.remove(session.get(User.class, id));
        session.flush();
        return true;
    }

    @Override
    public boolean update(User entity) {
        @Cleanup var session = sessionFactory.openSession();
        session.merge(entity);
        return true;
    }

    @Override
    public Optional<User> findById(Integer id) {
        @Cleanup var session = sessionFactory.openSession();
        return Optional.ofNullable(session.get(User.class, id));
    }

    @Override
    public List<User> findAll() {
        @Cleanup var session = sessionFactory.openSession();
        return session.createQuery("from User", User.class).list();
    }

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        @Cleanup var session = sessionFactory.openSession();
        var query = session.createQuery("select u from User u where u.email = :email and u.password = :password")
                .setParameter("email", email)
                .setParameter("password", password);
        User user = (User) query.uniqueResult();
        return Optional.ofNullable(user);
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
