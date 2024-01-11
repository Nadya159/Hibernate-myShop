package by.javaguru.dao;

import by.javaguru.entity.Item;
import by.javaguru.entity.Order;
import by.javaguru.utils.ConnectionManager;
import by.javaguru.utils.HibernateUtil;
import org.hibernate.SessionFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDao implements Dao<Order, Item> {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final static ItemDao INSTANCE = new ItemDao();
    private final static OrderDao orderDao = OrderDao.getInstance();
    private final static ProductDao productDao = ProductDao.getInstance();

    @Override
    public boolean update(Item item) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.merge(item);
            transaction.commit();
            return true;
        }
    }

    @Override
    public List<Item> findAll() {
        try (var session = sessionFactory.getCurrentSession();) {
            return session.createQuery("from Item", Item.class).list();
        }
    }

    private Item buildItem(ResultSet result) throws SQLException {
        return Item.builder()
                .order(orderDao.findById(result.getInt("order_id"),
                        result.getStatement().getConnection()).orElse(null))
                .product(productDao.findById(result.getInt("product_id"),
                        result.getStatement().getConnection()).orElse(null))
                .quantity(result.getInt("quantity"))
                .priceOrder(result.getBigDecimal("price_order"))
                .build();
    }

    @Override
    public Optional<Item> findById(Order order) {
        try (var session = sessionFactory.getCurrentSession()) {
            Item item = session.get(Item.class, id);
            return Optional.ofNullable(item);
        }
    }

    @Override
    public Item save(Item item) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.persist(item);
            transaction.commit();
            return item;
        }
    }

    @Override
    public boolean delete(Order order) {
        try (var session = sessionFactory.getCurrentSession()) {
            var transaction = session.beginTransaction();
            session.delete(session.get(Item.class, id));
            transaction.commit();
            return true;
        }
    }

    public static ItemDao getInstance() {
        return INSTANCE;
    }

    private ItemDao() {
    }
}
