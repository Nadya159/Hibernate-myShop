package by.javaguru.dao;

import by.javaguru.entity.BaseEntity;
import by.javaguru.utils.HibernateUtil;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class DaoBase<K extends Serializable, E extends BaseEntity> implements Dao<K, E> {
    private Class<E> eClass;
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    protected Class<E> getEClass(){
        if (eClass == null){
            eClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        }
        return eClass;
    }

    @Override
    public E save(E entity) {
        @Cleanup var session = sessionFactory.openSession();
        session.save(entity);
        return entity;
    }

    @Override
    public boolean delete(K id) {
        @Cleanup var session = sessionFactory.openSession();
        session.remove(id);
        session.flush();
        return true;
    }

    @Override
    public boolean update(E entity) {
        @Cleanup var session = sessionFactory.openSession();
        session.merge(entity);
        return true;
    }

    @Override
    public Optional<E> findById(K id) {
        @Cleanup var session = sessionFactory.openSession();
        return Optional.ofNullable(session.find(eClass, id));
    }

    @Override
    public List<E> findAll() {
        @Cleanup var session = sessionFactory.openSession();
        var criteria = session.getCriteriaBuilder().createQuery(eClass);
        criteria.from(eClass);
        return session.createQuery(criteria).getResultList();
    }
}
