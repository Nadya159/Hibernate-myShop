package by.javaguru.dao;


import by.javaguru.entity.Gender;
import by.javaguru.entity.Role;
import by.javaguru.entity.User;
import by.javaguru.utils.ConnectionManager;
import by.javaguru.utils.HibernateUtil;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Integer, User> {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final static UserDao INSTANCE = new UserDao();

    @Override
    public boolean update(User user) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            return true;
        }
    }

    @Override
    public List<User> findAll() {
        try (var session = sessionFactory.getCurrentSession();) {
            return session.createQuery("from User", User.class).list();
        }
    }

    private static User buildUser(ResultSet result) throws SQLException {
        return User.builder()
                .id(result.getInt("id"))
                .name(result.getString("name"))
                .phone(result.getString("phone"))
                .email(result.getString("email"))
                .role(Role.valueOf(result.getString("role")))
                .gender(Gender.valueOf(result.getString("gender")))
                .birthday(LocalDate.parse(result.getString("birthday")))
                .password(result.getString("password"))
                .build();
    }

    @Override
    public Optional<User> findById(Integer id) {
        try (var session = sessionFactory.getCurrentSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        }
    }

    @Override
    @SneakyThrows
    public User save(User user) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            return true;
        }
    }

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            var user = session.createQuery("from User u where u.email = :email and u.password = :password")
                    .setParameter("email", email)
                    .setParameter("password", password);
            //preparedStatement.setString(1, email);
            //preparedStatement.setString(2, password);
            //var resultSet = preparedStatement.executeQuery();
            //User user = null;
            //if (resultSet.next()) {
            //    user = buildEntity(resultSet);
            //}
            return Optional.ofNullable(user);
        }
    }

    private User buildEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getObject("id", Integer.class))
                .name(resultSet.getObject("name", String.class))
                .birthday(resultSet.getObject("birthday", Date.class).toLocalDate())
                .email(resultSet.getObject("email", String.class))
                .password(resultSet.getObject("password", String.class))
                .role(Role.find(resultSet.getObject("role", String.class)).orElse(null))
                .gender(Gender.valueOf(resultSet.getObject("gender", String.class)))
                .build();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    private UserDao() {
    }
}


