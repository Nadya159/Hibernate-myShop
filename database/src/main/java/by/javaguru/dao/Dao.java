package by.javaguru.dao;

import by.javaguru.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Dao<K extends Serializable, E extends BaseEntity> {
    E save(E entity);

    boolean delete(K id);

    boolean update(E entity);

    Optional<E> findById(K id);

    List<E> findAll();
}
