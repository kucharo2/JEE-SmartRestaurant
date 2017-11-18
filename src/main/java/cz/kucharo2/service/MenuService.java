package cz.kucharo2.service;

import cz.kucharo2.data.entity.Category;
import cz.kucharo2.data.entity.Item;
import cz.kucharo2.data.enums.CategoryType;

import java.util.List;
import java.util.Map;

/**
 * Created by Roman on 12/3/2014.
 */
public interface MenuService {
	/**
	 * Returns list of all categories by parent category
	 *
	 * @param category parent category
	 * @return list of categories
	 */
	List<Category> getAllCategoriesByParentCategory(Category category);

	/**
	 * Returns list of all categories by parent category code
	 *
	 * @param categoryType parent category type
	 * @return list of all categories
	 */
	List<Category> getAllCategoriesByParentCategory(CategoryType categoryType);

	/**
	 * Returns map of category-dishes pairs = category as key and its corresponding dishes as value
	 * @param categoryType category type
	 * @return map of category dishes pairs
	 */
	Map<Category, List<Item>> getAllItemsByCategoryCode(CategoryType categoryType);

	/**
	 * Returns map of category name -dishes pairs = category name as key and its corresponding dishes as value
	 * @param categoryType category type
	 * @return map of category dishes pairs
	 */
	Map<String, List<Item>> getAllItemsByCategoryCodeKeyedByCategoryName(CategoryType categoryType);

	/**
	 * Returns list of items by category
	 *
	 * @param code category code
	 * @return list of items
	 */
	List<Item> getItemsByCategory(CategoryType code);

	/**
	 * Returns correct combinations to selected item with specific category
	 *
	 * @param itemId item id
	 * @param category category
	 * @return list of items
	 */
	List<Item> getItemsByCombinationToAndCategory(int itemId, CategoryType category);

	Item getItemById(int id);

}
