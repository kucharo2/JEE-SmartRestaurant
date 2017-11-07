package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.ItemDao;
import cz.kucharo2.data.entity.Category;
import cz.kucharo2.data.entity.Item;
import cz.kucharo2.data.enums.CategoryType;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
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
        String query = Item.CATEGORY_ID + " = (select " + Category.CATEGORY_ID + " from " + Category.TABLE_NAME +
                " where " + Category.CODE + " = :categoryType)";

        Map<String, Object> params = new HashMap<>();
        params.put("categoryType", categoryType.name());

        return getByWhereCondition(query, params);
    }

    @Override
    public Long getItemsByCategoryCount(CategoryType categoryType) {
        String query = Item.CATEGORY_ID + " = (select " + Category.CATEGORY_ID + " from " + Category.TABLE_NAME +
                " where " + Category.CODE + " = :categoryType)";

        Map<String, Object> params = new HashMap<>();
        params.put("categoryType", categoryType.name());

        return getCountByCondition(query, params);
    }

    @Override
    public List<Item> getItemsWithCombinations(List<Integer> itemsIds) {
        //TODO implement for entity manager
//        Session openSession = getOpenSession();
//        Criteria crit = openSession.createCriteria(Item.class)
//                .add(Restrictions.in("id", itemsIds))
//                .setFetchMode("itemCombination", FetchMode.JOIN)
//                .setFetchMode("itemCombinationTo", FetchMode.JOIN);
//        List<Item> items = (List<Item>) crit.list();
//        return closeSessionAndReturnValue(items, openSession);
        return new ArrayList<>();
    }
}
