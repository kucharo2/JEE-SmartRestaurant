package cz.kucharo2.service;

import cz.kucharo2.data.entity.Category;
import cz.kucharo2.data.entity.Item;
import cz.kucharo2.data.enums.CategoryType;

import java.util.List;

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
	 * Returns list of items by category
	 *
	 * @param code category code
	 * @return list of items
	 */
	List<Item> getItemsByCategory(CategoryType code);

	/**
	 * Returns correct combinations to selected items in bill for specific category
	 *
	 * @param billId bill id
	 * @param category category
	 * @return list of items
	 */
	List<Item> getItemsByCombinationToAndCategory(int billId, CategoryType category);

	Item getItemById(int id);

}
