package by.javaguru.dao;

import by.javaguru.entity.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDao extends DaoBase<Integer, Order> {
    private static final OrderDao INSTANCE = new OrderDao();


    /**
     * Все незакрытые заказы
     */
    public List<String> findOrderByStatus(Session session) {
        return session.createQuery("select o.address " +
                                   "from Order o " +
                                   "where o.status = 'CREATED' or o.status = 'PROGRESS'", String.class)
                .list();
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
