package dao;

import by.javaguru.dao.ItemDao;
import by.javaguru.utils.HibernateUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import utils.TestDataImporter;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Slf4j
@TestInstance(PER_CLASS)
class ItemDaoTest {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final ItemDao itemDao = ItemDao.getInstance();

    @BeforeAll
    void initDb() {
        TestDataImporter.importData(sessionFactory);
        sessionFactory.openSession();
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    void findProductSold() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<String> results = itemDao.findProductSold(session);
        assertThat(results).hasSize(3);

        List<String> activeOrder = results.stream().collect(toList());
        assertThat(activeOrder).contains("Батон", "Круассан", "Булка");
        session.getTransaction().commit();
    }
}
