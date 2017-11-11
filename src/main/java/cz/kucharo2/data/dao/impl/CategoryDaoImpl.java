package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.CategoryDao;
import cz.kucharo2.data.entity.Category;
import cz.kucharo2.data.enums.CategoryType;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.enterprise.context.ApplicationScoped;

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
        Session session = (Session) getEntityManager().getDelegate();
        Criteria crit = session.createCriteria(Category.class)
                .add(Restrictions.eq("code", code))
                .setFetchMode("childCategories", FetchMode.JOIN);
        return (Category) crit.uniqueResult();
    }
}
