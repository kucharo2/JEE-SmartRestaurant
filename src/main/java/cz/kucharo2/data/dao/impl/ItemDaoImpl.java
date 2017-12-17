package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.ItemDao;
import cz.kucharo2.data.entity.Category;
import cz.kucharo2.data.entity.Item;
import cz.kucharo2.data.enums.CategoryType;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@ApplicationScoped
public class ItemDaoImpl extends AbstractGenericDaoImpl<Item> implements ItemDao {

    public ItemDaoImpl() {
        super(Item.class);
    }

    @Override
    public List<Item> getItemsByCategory(CategoryType categoryType) {
        String query = "e.category.code = :code";
        Map<String, Object> params = new HashMap<>();
        params.put("code", categoryType);

        return getByWhereCondition(query, params);
    }

    @Override
    public List<Item> getItemsByListCategories(List<CategoryType> categories) {
        String query = "e.category.code IN (:code)";
        Map<String, Object> params = new HashMap<>();
        params.put("code", categories);

        return getByWhereCondition(query, params);
    }

    @Override
    public Long getItemsByCategoryCount(CategoryType categoryType) {
        String query = "e.category.id = (select i.id from " + Category.TABLE_NAME + " i where i."
                + Category.CODE + " = :categoryType)";

        Map<String, Object> params = new HashMap<>();
        params.put("categoryType", categoryType);

        return getCountByCondition(query, params);
    }

    @Override
    public Item getItemsWithCombinations(Integer itemId) {
        Session session = (Session) getEntityManager().getDelegate();
        Criteria crit = session.createCriteria(Item.class)
                .add(Restrictions.eq("id", itemId))
                .setFetchMode("itemCombination", FetchMode.JOIN)
                .setFetchMode("itemCombinationTo", FetchMode.JOIN);
        return (Item) crit.uniqueResult();
    }
}
