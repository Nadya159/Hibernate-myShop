package by.javaguru.dao;

import by.javaguru.entity.Order;
import by.javaguru.utils.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class OrderRepository extends RepositoryBase<Integer, Order> {
    private static final EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();
    private static final OrderRepository INSTANCE = new OrderRepository(entityManager);
    public OrderRepository(EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * Все незакрытые заказы
     */
    public List<String> findOrderByStatus(EntityManager entityManager) {
        return entityManager.createQuery("select o.address " +
                                         "from Order o " +
                                         "where o.status = 'CREATED' or o.status = 'PROGRESS'", String.class)
                .getResultList();
    }

    public static OrderRepository getInstance() {
        return INSTANCE;
    }
}
