package by.javaguru.dao;

import by.javaguru.entity.Item;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemDao extends DaoBase<Integer, Item> {
    private final static ItemDao INSTANCE = new ItemDao();

    /**
     * Наименования проданных продуктов
     */
    public List<String> findProductSold(Session session) {
        return session.createQuery("select i.product.name " +
                                   "from Item i " +
                                   "where i.order.status != ('CANCELLED')", String.class)
                .list();
    }


    public static ItemDao getInstance() {
        return INSTANCE;
    }
}
