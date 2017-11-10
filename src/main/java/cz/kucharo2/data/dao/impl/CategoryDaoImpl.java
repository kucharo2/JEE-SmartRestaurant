package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.CategoryDao;
import cz.kucharo2.data.entity.Category;
import cz.kucharo2.data.enums.CategoryType;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

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
        String query = Category.CODE + " = :code";
        Map<String, Object> params = new HashMap<>();
        params.put("code", code.name());
        
        return getByWhereConditionSingleResult(query, params);
    }
}
