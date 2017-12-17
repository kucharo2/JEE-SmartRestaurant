package cz.kucharo2.data.dao;

import cz.kucharo2.data.entity.DtoEntity;

import java.util.List;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public interface AbstractGenericDao<T extends DtoEntity> {

    T getById(Integer id);

    void createOrUpdate(T dtoEntity);

    boolean delete(T dtoEntity);

    List<T> getAll();

    Long getAllCount(T dtoEntity);

}
