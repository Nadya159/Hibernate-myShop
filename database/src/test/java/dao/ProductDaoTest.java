package dao;

import by.javaguru.dao.ProductDao;
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
class ProductDaoTest {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final ProductDao productDao = ProductDao.getInstance();
    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
        sessionFactory.openSession();
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    void findProductWithZeroBalance(){
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<String> results = productDao.findProductWithZeroBalance(session);
        assertThat(results).hasSize(2);

        List<String> products = results.stream().collect(toList());
        assertThat(products).contains("Слойка", "Плюшка");
        session.getTransaction().commit();
    }
}
