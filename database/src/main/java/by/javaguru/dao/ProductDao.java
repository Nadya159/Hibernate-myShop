package by.javaguru.dao;

import by.javaguru.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDao extends DaoBase<Integer, Product> {
    private final static ProductDao INSTANCE = new ProductDao();

    /**
     * Количество продуктов с нулевым остатком
     */
    public List<String> findProductWithZeroBalance(Session session) {
        return session.createQuery("select p.name " +
                                   "from Product p " +
                                   "where p.balance = 0", String.class)
                .list();
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
