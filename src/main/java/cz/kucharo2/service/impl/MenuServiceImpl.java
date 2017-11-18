package cz.kucharo2.service.impl;

import cz.kucharo2.data.dao.BillDao;
import cz.kucharo2.data.dao.CategoryDao;
import cz.kucharo2.data.dao.ItemDao;
import cz.kucharo2.data.entity.Category;
import cz.kucharo2.data.entity.Item;
import cz.kucharo2.data.enums.CategoryType;
import cz.kucharo2.service.MenuService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Roman on 12/3/2014.
 */
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Inject
    private CategoryDao categoryDao;

    @Inject
    private ItemDao itemDao;

    @Inject
    private BillDao billDao;

    @Override
    public List<Category> getAllCategoriesByParentCategory(Category category) {
        return getAllCategoriesByParentCategory(category.getCode());
    }

    @Override
    public List<Category> getAllCategoriesByParentCategory(CategoryType categoryType) {
        return (List<Category>) categoryDao.getCategoryByCode(categoryType).getChildCategories();
    }

    @Override
    public Map<Category, List<Item>> getAllDishesByCategoryCode(CategoryType categoryType) {
        Collection<Category> list = categoryDao.getCategoryByCode(categoryType).getChildCategories();
        return list.stream().collect(Collectors.toMap(Function.identity(), c->itemDao.getItemsByCategory(c.getCode())));
    }

    @Override
    public Map<String, List<Item>> getAllDishesByCategoryCodeKeyedByCategoryName(CategoryType categoryType) {
        Collection<Category> list = categoryDao.getCategoryByCode(categoryType).getChildCategories();
        return list.stream().collect(Collectors.toMap(Category::getName, c -> itemDao.getItemsByCategory(c.getCode())));
    }

    @Override
    public List<Item> getItemsByCategory(CategoryType code) {
        return itemDao.getItemsByCategory(code);
    }

    @Override
    public List<Item> getItemsByCombinationToAndCategory(int itemId, CategoryType categoryType) {
        Item itemWithCombinations = itemDao.getItemsWithCombinations(itemId);

        Set<Item> combinations = new HashSet<>();

        combinations.addAll(filterCombinationsByCategory(itemWithCombinations.getItemCombination(), categoryType));
        combinations.addAll(filterCombinationsByCategory(itemWithCombinations.getItemCombinationTo(), categoryType));

        return new ArrayList<>(combinations);
    }

    private List<Item> filterCombinationsByCategory(Set<Item> combinations, CategoryType categoryType) {
        return combinations
                .stream()
                .filter(item -> categoryType == null || item.getCategory().getCode().equals(categoryType))
                .collect(Collectors.toList());

    }

    @Override
    public Item getItemById(int id) {
        return itemDao.getById(id);
    }

}
