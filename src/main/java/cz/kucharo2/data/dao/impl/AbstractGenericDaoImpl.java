package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.AbstractGenericDao;
import cz.kucharo2.data.entity.DtoEntity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public abstract class AbstractGenericDaoImpl<T extends DtoEntity> implements AbstractGenericDao<T> {

    @PersistenceContext(name = "primary")
    private EntityManager entityManager;

    private Class<T> clazz;

    public AbstractGenericDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T getById(Integer id) {
        return entityManager.find(clazz, id);
    }

    @Override
    public boolean delete(T entity) {
        try {
            entityManager.remove(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void createOrUpdate(T dtoEntity) {
        Integer id = dtoEntity.getId();
        if (id != null) {
            entityManager.merge(dtoEntity);
        } else {
            entityManager.persist(dtoEntity);
        }
    }

    @Override
    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = criteriaBuilder.createQuery(clazz);
        Root<T> entity = cq.from(clazz);
        cq.select(entity);
        TypedQuery<T> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Long getAllCount(T dtoEntity) {
        Query query = getEntityManager().createQuery("select count(e) from " + dtoEntity.getClass().getName() + " e" );
        return (Long) query.getSingleResult();
    }

    protected Long getCountByCondition(String whereCondition, Map<String, Object> sqlParams) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(e) from " + clazz.getAnnotation(Table.class).name()).append(" e WHERE ");
        sb.append(whereCondition);
        Query query = getEntityManager().createQuery(sb.toString());

        for (String key : sqlParams.keySet()) {
            query.setParameter(key, sqlParams.get(key));
        }
        return (Long) query.getSingleResult();
    }


    protected List<T> getByWhereCondition(String whereCondition, Map<String, Object> sqlParams) {
        try {
            return createGetWhereConditionQuery(whereCondition, sqlParams).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    protected T getByWhereConditionSingleResult(String whereCondition, Map<String, Object> sqlParams) {
        try {
            return (T) createGetWhereConditionQuery(whereCondition, sqlParams).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    protected Query createGetWhereConditionQuery(String whereCondition, Map<String, Object> sqlParams) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT e FROM ").append(clazz.getAnnotation(Table.class).name()).append(" e WHERE ");
        sb.append(whereCondition);
        Query query = entityManager.createQuery(sb.toString());
        for (String key : sqlParams.keySet()) {
            query.setParameter(key, sqlParams.get(key));
        }
        return query;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }


}
