package dao;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import by.javaguru.dao.UserDao;
import by.javaguru.utils.HibernateUtil;
import utils.TestDataImporter;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Slf4j
@TestInstance(PER_CLASS)
class UserDaoTest {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final UserDao userDao = UserDao.getInstance();
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
    void findUserInGender(){
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Object[]> results = userDao.findUserInGender(session);
        assertThat(results).hasSize(2);

        List<Long> countMale = results.stream().map(it -> (Long) it[1]).collect(toList());
        assertThat(countMale).contains(2L, 3L);
        session.getTransaction().commit();
    }
}
