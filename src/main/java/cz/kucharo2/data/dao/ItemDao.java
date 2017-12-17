package cz.kucharo2.data.dao;

import cz.kucharo2.data.entity.Item;
import cz.kucharo2.data.enums.CategoryType;

import java.util.List;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public interface ItemDao extends AbstractGenericDao<Item> {

	/**
	 * Return list of dishes by specific category
	 *
	 * @param categoryType category type
	 * @return list of dishes by category
	 *
	 */
	List<Item> getItemsByCategory(CategoryType categoryType);

	/**
	 * Return list of dishes by specific categories
	 *
	 * @param categories list of category type
	 * @return list of dishes by categories
	 */
	List<Item> getItemsByListCategories(List<CategoryType> categories);

	/**
	 * Return count of dishes by specific category
	 *
	 * @param categoryType category type
	 * @return count of dishes
	 *
	 */
	Long getItemsByCategoryCount(CategoryType categoryType);

	/**
	 * Get items with lazy loaded possible combinations for it.
	 *
	 * @param itemId item id
	 * @return list of items
	 */
	Item getItemsWithCombinations(Integer itemId);

}
