package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.CategoryDao;
import cz.kucharo2.data.entity.Category;
import cz.kucharo2.data.enums.CategoryType;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@ApplicationScoped
public class CategoryDaoImpl extends AbstractGenericDaoImpl<Category> implements CategoryDao {

    public CategoryDaoImpl() {
        super(Category.class);
    }

    @Override
    public Category getCategoryByCode(CategoryType code) {
        Query query = getEntityManager().createQuery("select i from Category i join fetch i.childCategories " +
                "where i.code = :code").setParameter("code", code);
        return (Category) query.getSingleResult();
    }
}
