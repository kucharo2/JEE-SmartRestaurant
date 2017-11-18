package cz.kucharo2.service.impl;

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

    @Override
    public List<Category> getAllCategoriesByParentCategory(Category category) {
        return getAllCategoriesByParentCategory(category.getCode());
    }

    @Override
    public List<Category> getAllCategoriesByParentCategory(CategoryType categoryType) {
        return (List<Category>) categoryDao.getCategoryByCode(categoryType).getChildCategories();
    }

    @Override
    public Map<Category, List<Item>> getAllItemsByCategoryCode(CategoryType categoryType) {
        Map<Category, List<Item>> map = new LinkedHashMap<>();
        Collection<Category> categories = categoryDao.getCategoryByCode(categoryType).getChildCategories();
        List<CategoryType> categoryTypes = new ArrayList<>();

		for (Category category : categories) {
            categoryTypes.add(category.getCode());
            map.put(category, new ArrayList<>());
        }

        List<Item> items = itemDao.getItemsByListCategories(categoryTypes);
        for (Item item : items) {
            if (map.containsKey(item.getCategory())){
                List<Item> currentItems = map.get(item.getCategory());
                currentItems.add(item);
            }
        }

        return map;
    }

    @Override
    public Map<String, List<Item>> getAllItemsByCategoryCodeKeyedByCategoryName (CategoryType categoryType){
        Collection<Category> categories = categoryDao.getCategoryByCode(categoryType).getChildCategories();
        List<CategoryType> categoryTypes = new ArrayList<>();
        Map<String, List<Item>> map = new LinkedHashMap<>();

        for (Category category : categories) {
            categoryTypes.add(category.getCode());
            map.put(category.getName(), new ArrayList<>());
        }

        List<Item> items = itemDao.getItemsByListCategories(categoryTypes);
        for (Item item : items) {
            if (map.containsKey(item.getCategory().getName())){
                List<Item> currentItems = map.get(item.getCategory().getName());
                currentItems.add(item);
            }
        }

        return map;
    }

    @Override
    public List<Item> getItemsByCategory(CategoryType code) {
        return itemDao.getItemsByCategory(code);
    }


    @Override
    public List<Item> getItemsByCombinationToAndCategory ( int itemId, CategoryType categoryType){
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
    public Item getItemById ( int id){
        return itemDao.getById(id);
    }
}