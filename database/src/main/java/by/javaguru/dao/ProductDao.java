package by.javaguru.dao;

import by.javaguru.entity.Product;
import by.javaguru.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class ProductDao implements Dao<Integer, Product> {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final EntityManager entityManager = sessionFactory.openSession();
    private final static ProductDao INSTANCE = new ProductDao();


    public Product save(Product entity) {
        entityManager.persist(entity);
        return entity;
    }

    public boolean delete(Integer id) {
        entityManager.remove(id);
        entityManager.flush();
        return true;
    }

    public boolean update(Product entity) {
        entityManager.merge(entity);
        return true;
    }

    public Optional<Product> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Product.class, id));
    }

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("from Product ", Product.class).getResultList();
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
