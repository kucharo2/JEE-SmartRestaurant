package cz.kucharo2.data.dao;

import cz.kucharo2.data.entity.Category;
import cz.kucharo2.data.enums.CategoryType;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public interface CategoryDao extends AbstractGenericDao<Category> {

	/**
	 * Return category by specific code
	 *
	 * @param code category code
	 * @return category
	 */
	Category getCategoryByCode(CategoryType code);
}
