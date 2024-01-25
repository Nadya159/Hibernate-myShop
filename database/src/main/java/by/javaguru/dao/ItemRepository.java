package by.javaguru.dao;

import by.javaguru.entity.Item;
import by.javaguru.utils.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ItemRepository extends RepositoryBase<Integer, Item> {
    private static final EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();
    private final static ItemRepository INSTANCE = new ItemRepository(entityManager);

    public ItemRepository(EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * Наименования проданных продуктов
     */
    public List<String> findProductSold(EntityManager entityManager) {
        return entityManager.createQuery("select i.product.name " +
                                         "from Item i " +
                                         "where i.order.status != ('CANCELLED')", String.class)
                .getResultList();
    }

    public static ItemRepository getInstance() {
        return INSTANCE;
    }
}
