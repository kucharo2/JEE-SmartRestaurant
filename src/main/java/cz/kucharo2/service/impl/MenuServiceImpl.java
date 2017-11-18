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
import java.util.stream.Collectors;

/**
 * Created by Roman on 12/3/2014.
 */
@ApplicationScoped
@Transactional
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
	public Map<CategoryType, List<Item>> getAllDishesByCode(CategoryType categoryType) {
		Map map = new HashMap<CategoryType, List<Item>>();
		List list = (List) categoryDao.getCategoryByCode(categoryType).getChildCategories();
		for (Iterator<Category> i = list.iterator(); i.hasNext(); ) {
			Category category = i.next();
			map.put(category.getCode(), itemDao.getItemsByCategory(category.getCode()));
		}

		return map;
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
