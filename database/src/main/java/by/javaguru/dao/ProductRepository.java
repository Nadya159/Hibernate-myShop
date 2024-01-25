package by.javaguru.dao;

import by.javaguru.entity.Product;
import by.javaguru.utils.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductRepository extends RepositoryBase<Integer, Product> {
    private static final EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();
    private static final ProductRepository INSTANCE = new ProductRepository(entityManager);

    public ProductRepository(EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * Количество продуктов с нулевым остатком
     */
    public List<String> findProductWithZeroBalance(EntityManager entityManager) {
        return entityManager.createQuery("select p.name " +
                                         "from Product p " +
                                         "where p.balance = 0", String.class)
                .getResultList();
    }

    public static ProductRepository getInstance() {
        return INSTANCE;
    }
}
