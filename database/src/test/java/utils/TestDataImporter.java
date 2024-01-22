package utils;

import by.javaguru.entity.*;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@UtilityClass
public class TestDataImporter {
    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        User test1 = saveUser(session, "test1", "79069666166", "test1@mail.ru", Role.USER,
                Gender.MALE, LocalDate.of(2002, 10, 20), "123");
        User test2 = saveUser(session, "test2", "79069666122", "test2@mail.ru", Role.USER,
                Gender.MALE, LocalDate.of(2005, 8, 10), "123");
        User test3 = saveUser(session, "test3", "79069666123", "test3@mail.ru", Role.USER,
                Gender.FEMALE, LocalDate.of(2000, 7, 15), "123");
        User test4 = saveUser(session, "test4", "79069666124", "test4@mail.ru", Role.USER,
                Gender.MALE, LocalDate.of(2003, 5, 10), "123");
        User test5 = saveUser(session, "test5", "79069666125", "test5@mail.ru", Role.USER,
                Gender.FEMALE, LocalDate.of(1999, 3, 30), "123");

        Product product1 = saveProduct(session, "Булка", BigDecimal.valueOf(15.00), 20);
        Product product2 = saveProduct(session, "Батон", BigDecimal.valueOf(25.00), 50);
        Product product3 = saveProduct(session, "Слойка", BigDecimal.valueOf(18.00), 0);
        Product product4 = saveProduct(session, "Плюшка", BigDecimal.valueOf(28.00), 0);
        Product product5 = saveProduct(session, "Круассан", BigDecimal.valueOf(17.00), 7);

        Order order1 = saveOrder(session, test1, LocalDateTime.of(2024, Month.JANUARY,20, 15, 20, 15),
                LocalDateTime.of(2024, Month.JANUARY,20, 17, 30, 15), "Калинина, 10-28",
                OrderStatus.CLOSED);
        Order order2 = saveOrder(session, test3, LocalDateTime.of(2024, Month.JANUARY,15, 11, 20, 10),
                null, "Ленина, 18-58",
                OrderStatus.PROGRESS);
        Order order3 = saveOrder(session, test3, LocalDateTime.of(2024, Month.JANUARY,21, 10, 20, 25),
                null, "Ленина, 18-58",
                OrderStatus.CREATED);

        Item item1 = saveItem(session, order1, product2, 20, BigDecimal.valueOf(36.0));
        Item item2 = saveItem(session, order1, product5, 5, BigDecimal.valueOf(29.0));
        Item item3 = saveItem(session, order2, product1, 10, BigDecimal.valueOf(26.0));

        session.flush();
    }

    private User saveUser(Session session, String name, String phone, String email, Role role, Gender gender,
                          LocalDate birthday, String pswd) {
        User user = User.builder()
                .name(name)
                .phone(phone)
                .email(email)
                .role(role)
                .gender(gender)
                .birthday(birthday)
                .password(pswd)
                .build();
        session.save(user);
        return user;
    }

    private Product saveProduct(Session session, String name, BigDecimal price, Integer balance){
        Product product = Product.builder()
                .name(name)
                .price(price)
                .balance(balance)
                .build();
        session.save(product);
        return product;
    }

    private Order saveOrder(Session session, User user, LocalDateTime createDate, LocalDateTime deliveryDate,
                            String address, OrderStatus status){
        Order order = Order.builder()
                .user(user)
                .createDate(createDate)
                .deliveryDate(deliveryDate)
                .address(address)
                .status(status)
                .build();
        session.save(order);
        return order;
    }

    private Item saveItem(Session session, Order order, Product product, Integer quantity, BigDecimal price){
        Item item = Item.builder()
                .order(order)
                .product(product)
                .quantity(2)
                .priceOrder(BigDecimal.valueOf(30))
                .build();
        session.save(item);
        return item;
    }

}
