package by.javaguru.dao;

import by.javaguru.utils.HibernateUtil;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class DaoBase<K, E> implements Dao<K, E> {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final Class<E> eClass;


    @Override
    public E save(E entity) {
        @Cleanup var session = sessionFactory.getCurrentSession();
        var transaction = session.beginTransaction();
        session.persist(entity);
        return entity;
    }

    @Override
    public boolean delete(K id) {
        @Cleanup var session = sessionFactory.getCurrentSession();
        var transaction = session.beginTransaction();
        session.delete(id);
        session.flush();
        return true;
    }

    @Override
    public boolean update(E entity) {
        @Cleanup var session = sessionFactory.getCurrentSession();
        var transaction = session.beginTransaction();
        session.merge(entity);
        return true;
    }

    @Override
    public Optional<E> findById(K id) {
        @Cleanup var session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.find(eClass, id));
    }

    @Override
    public List<E> findAll() {
        @Cleanup var session = sessionFactory.getCurrentSession();
        var transaction = session.beginTransaction();
        var criteria = session.getCriteriaBuilder().createQuery(eClass);
        criteria.from(eClass);
        return session.createQuery(criteria).list();
    }
}
