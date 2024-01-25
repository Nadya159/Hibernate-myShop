package by.javaguru.dao;

import by.javaguru.entity.User;
import by.javaguru.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import lombok.*;

import java.util.List;
import java.util.Optional;

public class UserRepository extends RepositoryBase<Integer, User> {
    private static final EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();
    private static final UserRepository INSTANCE = new UserRepository(entityManager);

    public UserRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        var query = entityManager.createQuery("select u from User u " +
                                              "where u.email = :email and u.password = :password")
                .setParameter("email", email)
                .setParameter("password", password);
        User user = (User) query.getSingleResult();
        return Optional.ofNullable(user);
    }

    /**
     * Количество пользователей каждого гендера
     */
    public List<Object[]> findUserInGender(EntityManager entityManager) {
        return entityManager.createQuery("select u.gender, count(u.id) " +
                                         "from User u " +
                                         "group by u.gender", Object[].class).getResultList();
    }

    public static UserRepository getInstance() {
        return INSTANCE;
    }
}
