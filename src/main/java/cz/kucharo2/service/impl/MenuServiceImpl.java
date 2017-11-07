package cz.kucharo2.service.impl;

import cz.kucharo2.data.dao.BillDao;
import cz.kucharo2.data.dao.CategoryDao;
import cz.kucharo2.data.dao.ItemDao;
import cz.kucharo2.data.entity.Bill;
import cz.kucharo2.data.entity.BillItem;
import cz.kucharo2.data.entity.Category;
import cz.kucharo2.data.entity.Item;
import cz.kucharo2.data.enums.CategoryType;
import cz.kucharo2.service.MenuService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Roman on 12/3/2014.
 */
@ApplicationScoped
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
	public List<Item> getItemsByCategory(CategoryType code) {
		return itemDao.getItemsByCategory(code);
	}

	@Override
	public List<Item> getItemsByCombinationToAndCategory(int billId, CategoryType category) {
		Bill bill = billDao.getById(billId);
		List<Integer> itemIds = new ArrayList<>();
		for (BillItem billItem : bill.getBillItems()) {
			itemIds.add(billItem.getItem().getId());
		}
		List<Item> itemsOnBill = itemDao.getItemsWithCombinations(itemIds);
		List<Item> combinationsByCategory = new ArrayList<>();
		Set<Item> combinations = getCombinationsToItems(itemsOnBill);
		for(Item item : combinations) {
			if(item.getCategory().getCode().equals(category)) {
				combinationsByCategory.add(item);
			}
		}
		return combinationsByCategory;
	}

	@Override
	public Item getItemById(int id) {
		return itemDao.getById(id);
	}

	private Set<Item> getCombinationsToItems(List<Item> items) {
		Set<Item> combinations = new HashSet<>();
		for(Item item : items) {
			combinations.add(item);
			combinations.addAll(item.getCombinations());
		}
		return combinations;
	}
}
